package com.Manage.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import org.restlet.data.Form;

/**
 * 数据对象转换：
 * <ol>
 * <li>实现{@link #org org.restlet.data.Form}与{@code }请求数据对象之间的相互转换；
 * <li>实现{@link #org org.restlet.data.Form}与{@code }服务数据对象之间的相互转换。
 * </ol>
 *
 */
public final class BeanConvertor
{
	/**
	 * 利用java反射机制把webForm中的属性值设置到bean中。
	 *
	 * @param webForm 待转换的webForm
	 * @param bean 转换成的业务Bean对象
	 */
	public static <T> void formToBeanObject(Form form, T bean)
	{
		// 如果form不存在或无属性存在直接返回
		if (null == form || null == form.getNames())
		{
			return;
		}

		// 遍历字段名称设值
		for (String fieldName : form.getNames())
		{
			setFieldValue(fieldName, form.getFirstValue(fieldName), bean);
		}
	}

	/**
	 * 利用java反射机制把webForm中的属性值设置到bean中。
	 *
	 * @param webForm 待转换的webForm
	 * @param bean 转换成的业务Bean对象
	 */
	public static <T> Form beanObjectToForm(T bean)
	{
		// 如果bean不存在或无属性存在直接返回
		if (null == bean)
		{
			return new Form();
		}

		// 先生成Form实例
		final Form form = new Form(8);

		// 遍历字段名称设值
		for (Field field : bean.getClass().getDeclaredFields())
		{
			// 常量不做转换
			if (Modifier.isStatic(field.getModifiers())
					&& Modifier.isFinal(field.getModifiers()))

			{
				continue;
			}

			Object valueObj = null;

			// 设置访问权限为允许访问
			field.setAccessible(true);
			try
			{
				valueObj = field.get(bean);
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			finally
			{
				field.setAccessible(false);
			}

			if (null == valueObj)
			{
				continue;
			}

			Class<?> clazz = field.getType();
			if (clazz.equals(String.class))
			{
				form.add(field.getName(), (String) valueObj);
			}
			else if (clazz.equals(Integer.class))
			{
				form.add(field.getName(), String.valueOf(valueObj));
			}
			else if (clazz.equals(Date.class))
			{
				form.add(
						field.getName(),
						DateUtils.formatDateTime((Date) valueObj));

			}
		}

		return form;
	}

	/**
	 * 利用java反射机制把值{@code (fieldValue)}设置到实例对象{@code (bean)}的字段
	 * {@code (fieldName)}中。
	 *
	 * @param fieldName 字段名
	 * @param fieldValue 字段值
	 * @param bean 待设置的对象实例
	 */
	public static <T> void setFieldValue(String fieldName, String fieldValue, T bean)
	{
		Field field = null;

		// 从当前的对象中获取指定名称的属性字段
		try
		{
			field = bean.getClass().getDeclaredField(fieldName);
		}
		catch (NoSuchFieldException e)
		{
			// 不做处理，继续检查继承的父类是否定义了该字段
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}

		//  检查父类中属性字段是否存在
		if (null == field)
		{
			// 获取父类类型
			Class<? extends Object> superClazz = bean.getClass().getSuperclass();

			// 如果不存在，则返回
			if (null == superClazz)
			{
				return;
			}

			try
			{
				field = bean.getClass().getSuperclass().getDeclaredField(fieldName);
			}
			catch (NoSuchFieldException e)
			{
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
		}

		// 如果都不存在，返回
		if (null == field)
		{
			return;
		}

		// 把传入的值fieldValue设置到对象实例中bean
		try
		{
			// 设置访问权限为允许访问
			field.setAccessible(true);

			// 根据对象属性的类型设置值
			if (field.getType().equals(String.class))
			{
				field.set(bean, fieldValue);
			}
			else if (field.getType().equals(Integer.class))
			{
				field.set(bean, Integer.valueOf(fieldValue));
			}
			else if (field.getType().equals(Date.class))
			{
				field.set(bean, fieldValue);
			}
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
		catch (IllegalAccessException e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 还原属性字段的访问权限
			if (null != field)
			{
				field.setAccessible(false);
			}
		}
	}
}
