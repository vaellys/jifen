<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>添加活动页面</title>
	<reps:theme />
</head>
<body>
<reps:container>
	<reps:panel id="first" dock="top" action="add.mvc" formId="form" validForm="true" style="width:800px">
		<reps:formcontent>
		
			<reps:formfield label="活动名称" labelStyle="width:20%;" textStyle="width:20%;">
				<reps:input name="name" maxLength="30" required="true"></reps:input>
			</reps:formfield>
			
			<reps:formfield label="活动分类" labelStyle="width:15%" textStyle="width:30%;">
	           <input type="hidden" name="jfRewardCategory.id" id="categoryId" value="" />
	            <reps:input required="true" id="parentCodeName" name="parentName" readonly="true"></reps:input>
	           <reps:dialog cssClass="btnLook" scrolling="true" width="300" id="chooseParentCode" iframe="true" url="choose.mvc" title="选择活动分类" value="选择活动分类" style="margin-left:-27px;" />
           </reps:formfield>
			
			<reps:formfield label="报名截至时间" labelStyle="width:20%;" textStyle="width:20%;">
				<reps:datepicker id="finishTime" name="finishTimeDisp" format="yyyy-MM-dd" required="true" min="${minDate }"></reps:datepicker>
		    </reps:formfield>
		    
		    <reps:formfield label="所需积分" labelStyle="width:15%" textStyle="width:30%;">
				<reps:input name="points" dataType="integernum" required="true"></reps:input>
			</reps:formfield>
			
		   <reps:formfield label="活动开始时间" labelStyle="width:20%;" textStyle="width:20%;">
				<reps:datepicker id="beginTime" name="beginTimeDisp" format="yyyy-MM-dd" required="true" min="${minDate }"></reps:datepicker>
		    </reps:formfield>
		    
		    <reps:formfield label="活动结束时间" labelStyle="width:15%;" textStyle="width:30%;" fullRow="true">
				<reps:datepicker id="endTime" name="endTimeDisp" format="yyyy-MM-dd" required="true" min="${minDate }"></reps:datepicker>
		    </reps:formfield>
		    
			<reps:formfield label="活动详情" fullRow="true">
				<reps:input name="description" maxLength="200" multiLine="true" style="width:515px;height:70px" required="true"></reps:input>
			</reps:formfield>
			
			<reps:formfield label="图片" labelStyle="width:15%" textStyle="width:30%;">
				<img name="img" width="128px",height="128px"/> <br>
				<reps:upload id="pictureid" callBack="getPathName" value="上传图片"  flagAbsolute="true"  path="${imageUploadPath}/jifen/activity" cssClass="uploading-a" fileType="png,jpg" coverage="true" size="2" reName="true"></reps:upload>
				<span id="tips"><font color="red">只能上传(png、jpg)格式,建议尺寸（200x240）2M以内</font></span>
				<input type="hidden" name="picture" id="pic"/>
           </reps:formfield>
		</reps:formcontent>
		<br/>
		<reps:formbar>
			<reps:ajax  messageCode="add.button.save" formId="form" callBack="my" type="button" cssClass="btn_save" beforeCall="checkFieldParams"></reps:ajax>
			<reps:button cssClass="btn_cancel_a" messageCode="add.button.cancel" onClick="back()"></reps:button>
		</reps:formbar>
       </div>
	</reps:panel>
</reps:container>
</body>
<script type="text/javascript">
	var my = function(data){
		messager.message(data, function(){ back(); });
	};
	
	function back() {
		window.location.href= "list.mvc";
	}
	
	var getPathName = function(filename, fileType, fileSize, path) {
		path = path.replaceAll("\\\\","/");
		var picture = path.replace("${imageUploadPath}","");
		$("input[name='picture']").val(picture);
		$("img[name='img']").attr("src", "${imagePath}" + picture);
		$('#tips').html("");
	};
	
	function checkFieldParams(){
		var beginTime = $("#beginTime").val();
		var endTime = $("#endTime").val();
		var finishTime = $("#finishTime").val();
		if(beginTime <= finishTime){
			messager.info("报名截止日期大于等于活动开始日期");
			return false;
		}
		if(beginTime > endTime){
			messager.info("活动开始日期大于活动结束日期");
			return false;
		}
		var picture = $("#pic").val();
		if(!picture){
  			messager.info("请上传活动图片！");
  			return false;
  		}
		return true;
	}
	
</script>
</html>