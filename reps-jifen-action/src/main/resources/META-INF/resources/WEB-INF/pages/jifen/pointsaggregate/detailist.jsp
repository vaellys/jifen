<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>积分分配详情</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="" id="top" dock="top" method="post" action="detailist.mvc?personId=${pointsCollect.personId}" formId="queryForm">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="开始时间"><reps:datepicker name="beginTimeDisp" format="yyyy-MM-dd" /></reps:formfield>
			<reps:formfield label="结束时间"><reps:datepicker name="endTimeDisp" format="yyyy-MM-dd" /></reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm" gridId="pointsCollectList" cssClass="search-form-a" beforeCall="checkFieldParams"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			<reps:button cssClass="return-a" onClick="back()" value="返回" />
			<span style="margin-left:20px;font-size:16px;font-weight:bold;color:red;">${pointsCollect.personName}</span>的积分详情
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="pointsCollectList" items="${list}" form="queryForm" var="collect" pagination="${pager}" flagSeq="true">
			<reps:gridrow>
				<reps:gridfield title="积分日期" width="15" align="center">
				${collect.getTime }
				</reps:gridfield>
				<reps:gridfield title="积分来源" width="25" align="center">${collect.getFrom }</reps:gridfield>
				<reps:gridfield title="积分数" width="15" align="center">${collect.points}</reps:gridfield>
				<reps:gridfield title="总获得积分" width="15" align="center">${collect.totalPoints}</reps:gridfield>
				<reps:gridfield title="可兑换积分" width="15" align="center">${collect.totalPointsUsable}</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
</body>
<script type="text/javascript">
	function back() {
		//返回列表页
		window.location.href = "list.mvc";
	}
	
	function checkFieldParams(){
		var beginTime = $("input[name='beginTimeDisp']").val();
		var endTime = $("input[name='endTimeDisp']").val();
		if("" != beginTime && "" != endTime){
			if(beginTime > endTime){
				messager.info("开始日期大于结束日期");
				return false;
			}
		}
		return true;
	}
</script>
</html>
