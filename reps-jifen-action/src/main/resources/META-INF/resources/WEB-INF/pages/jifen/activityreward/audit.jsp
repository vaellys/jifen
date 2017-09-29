<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
  <head>
     <title>活动审核</title>
	 <reps:theme/>
  </head>
  <body>
    <reps:container layout="true">
  	<reps:panel id="myform" dock="none" border="true" action="audit.mvc" formId="xform" validForm="true" method="post" title="活动审核设置">
		<reps:formcontent style="">
          	<reps:formfield label="是否通过" labelStyle="width:21%" textStyle="width:30%"  fullRow="true">
          		<input type="hidden" name="id"  value="${info.id }"></input>
          		<input type="radio" name="auditStatus" checked="checked" value="1">审核通过</input>
            	<input type="radio" name="auditStatus"  value="2">驳回</input>
			</reps:formfield>
			<reps:formfield label="审核意见" fullRow="true">
				<reps:input name="auditOpinion" maxLength="80" multiLine="true" style="width:300px;height:70px"></reps:input>
			</reps:formfield>
	   </reps:formcontent>
	    <reps:formbar style="margin-top:15px;">
  		   <reps:ajax confirm="你确定要提交吗？" formId="xform"  type="link" cssClass="btn_save_a" callBack="my"  messageCode="edit.button.save"/>
      	 </reps:formbar>
  	</reps:panel>
  </reps:container>
  <script type="text/javascript">
  
	var my = function(data){
		messager.message(data, function() {
			window.parent.location.href= "statistics.mvc?rewardId=${info.rewardId}";
		});
	}
  </script>
  </body>
</html>