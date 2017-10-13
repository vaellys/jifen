<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>积分统计查询</title>
	<reps:theme/>
</head>
<body>
<reps:container layout="true">
	<reps:panel title="" id="top" dock="top" method="post" action="list.mvc" formId="queryForm">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="学生姓名" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="personName" maxLength="30">${pointsAggregate.personName }</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm" gridId="pointsAggregateList" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<%-- <reps:footbar>
			<reps:button cssClass="export-a" id="exportExcel" value="数据导出" onClick="exportExcel();"></reps:button>
		</reps:footbar> --%>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="pointsAggregateList" items="${list}" form="queryForm" var="aggregate" pagination="${pager}" flagSeq="true">
			<reps:gridrow>
				<reps:gridfield title="姓名" width="15" align="center">${aggregate.personName}</reps:gridfield>
				<reps:gridfield title="学校" width="25" align="center">${aggregate.schoolName }</reps:gridfield>
				<reps:gridfield title="可兑换积分" width="15" align="center">${aggregate.totalPointsUsable}</reps:gridfield>
				<reps:gridfield title="总获得积分" width="15" align="center">${aggregate.totalPoints}</reps:gridfield>
				<reps:gridfield title="操作" width="10">
					<reps:button cssClass="detail-table"  value="积分详情" action="detailist.mvc?personId=${aggregate.personId}&personName=${aggregate.personName }" ></reps:button>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">
	var my = function(data){
		window.location.href= "list.mvc";
	};
</script>
</body>
</html>
