$.fn.extend({
	hoverTips : function (){
		var htmlDom="";
		var self = $(this);
		var sw = self.get(0).switch;
		if( !sw ) {
			sw = true;
			var content = self.attr("tooltips");
			htmlDom = $("<div class='tooltips'>")
					.addClass("tooltips-bg")
					.html("<p class='content'></p>"
							+ "<p class='triangle-front'></p>"
							+ "<p class='triangle-back'></p>");
			htmlDom.find("p.content").html( content );
		}
		self.on("mouseover",function(){
			
			$("body").append( htmlDom);
			var left = self.offset().left - htmlDom.outerWidth()/2 + self.outerWidth()/2;
			var top = self.offset().top - htmlDom.outerHeight() - parseInt(htmlDom.find(".triangle-front").css("border-width"));
			htmlDom.css({"left":left,"top":top - 10,"display":"block"});
			htmlDom.stop().animate({ "top" : top ,"opacity" : 1},300);
			
		});
		self.on("mouseout",function(){
			var top = parseInt(htmlDom.css("top"));
			htmlDom.stop().animate({ "top" : top - 10 ,"opacity" : 0},300,function(){
				htmlDom.remove();
				sw = false;
			});
		});
	},	
	hoverTipsEx : function (config){
		var htmlDom="";
		var self = $(this);
		var sw = self.get(0).switch;
		if( !sw ) {
			sw = true;
			var content;
			if(config!=null && config.isEscape!=null && config.isEscape){
				content = unescape(self.attr("tooltips"));
			} else {
				content = self.attr("tooltips");
			}
			var widthClass;
			if(config!=null && config.isEscape!=null && config.isSmallWidth){
				widthClass = 'tooltips'; //'tooltips-small'; 有问题, 未处理
			} else {
				widthClass = 'tooltips';
			}
			htmlDom = $("<div class='" + widthClass + "'>")
					.addClass("tooltips-bg")
					.html("<p class='content'></p>"
							+ "<p class='triangle-front'></p>"
							+ "<p class='triangle-back'></p>");
			htmlDom.find("p.content").html( content );
		}
		self.on("mouseover",function(){
			
			$("body").append( htmlDom);
			var left = self.offset().left - htmlDom.outerWidth()/2 + self.outerWidth()/2;
			var top = self.offset().top - htmlDom.outerHeight() - parseInt(htmlDom.find(".triangle-front").css("border-width"));
			htmlDom.css({"left":left,"top":top - 10,"display":"block"});
			htmlDom.stop().animate({ "top" : top ,"opacity" : 1},300);
			
		});
		self.on("mouseout",function(){
			var top = parseInt(htmlDom.css("top"));
			htmlDom.stop().animate({ "top" : top - 10 ,"opacity" : 0},300,function(){
				htmlDom.remove();
				sw = false;
			});
		});
	}

});

//example //$("#ahover").hoverTips();
//example //$("#aclick").clickTips();