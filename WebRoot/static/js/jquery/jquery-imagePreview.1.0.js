/*由于大图绑定在href属性中，故一般而言，需使用a标签的href指向大图。仅支持png,gif,jpg,bmp四种格式的图片。用法是：目标.preview();
例如：<a href="xx.jpg">图片</a>
$("a").preview();就可以了
*/
(function($){
	$.fn.preview = function(){
		var xOffset = 10;
		var yOffset = 20;
		var w = $(window).width();
		var h = $(window).height();
		
		$(this).each(function(){
			$(this).hover(function(e){
					$("body").append("<div id='preview'><div><img src='../images/loading.gif' width='250' height='250' /><p>"+$(this).attr('title')+"</p></div></div>");
				$("#preview > div>img").attr("src",$(this).attr('href'));
				
				$("#preview").css({
					position:"absolute",
					padding:"4px",
					border:"1px solid #f3f3f3",
					backgroundColor:"#eeeeee",
					top:(e.pageY - yOffset) + "px",
					top:"0px",
					zIndex:500
				});
				$("#preview > div").css({
					padding:"5px",
					backgroundColor:"white",
					border:"1px solid #cccccc"
				});
				$("#preview > div > p").css({
					textAlign:"center",
					fontSize:"12px",
					padding:"5px 0 1px",
					margin:"0"
				});
				
				if(e.pageX < w/2){
					$("#preview").css({
						left: e.pageX + xOffset + "px",
						right: "auto"
					}).fadeIn("fast");
				}else{
					$("#preview").css("right",(w - e.pageX + yOffset) + "px").css("left", "auto").fadeIn("fast");	
				}
			},function(){
				$("#preview").remove();
			}).mousemove(function(e){
				if(e.pageY+250>h){
			      $("#preview").css("top",(e.pageY-270) + "px").css("bottom", "auto").fadeIn("fast");	
				}else{
				   $("#preview").css("top",(e.pageY - xOffset) + "px")
				}
				if(e.pageX < w/2){
					$("#preview").css("left",(e.pageX + yOffset) + "px").css("right","auto");
				}else{
					$("#preview").css("right",(w - e.pageX + yOffset) + "px").css("left","auto");
				}
			});						  
		});
	};
})(jQuery);