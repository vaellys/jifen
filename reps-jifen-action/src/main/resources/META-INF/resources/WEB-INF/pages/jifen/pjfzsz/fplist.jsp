<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title></title>
	<reps:theme />
</head>
<body>
<reps:container layout="true">
	<reps:panel id="mytop" dock="top" action="${ctx}/reps/jifen/pjfzsz/fplist.mvc" method="post" formId="queryForm">
		<input type="hidden" id="behaviorId" name="behaviorId" value="${behaviorId}">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="教师名称" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="person.name">${query.person.name}</reps:input>
			</reps:formfield>
			<reps:formfield label="指标项" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="study.name">${query.study.name}</reps:input>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm"  gridId="mygrid" callBack="setDisabled" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			<reps:dialog cssClass="add-a" id="selectUser" iframe="true" width="600" height="360" value="选择教师"
						url="teachers.mvc?dialogId=selectUser&callBack=userCallBack"></reps:dialog>
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="mygrid" items="${list}" var="data" form="queryForm" flagSeq="false" pagination="${pager}">
			<reps:gridrow>
				<reps:gridfield title="教师名称" width="15" align="center">
					${data.person.name}
				</reps:gridfield>
				<reps:gridfield title="指标项" width="15" align="center">${data.study.item}</reps:gridfield>
				<reps:gridfield title="指标类型" width="15" align="center">
					<c:if test="${data.study.mark=='0'}">扣除</c:if>
					<c:if test="${data.study.mark=='1'}">奖励</c:if>
				</reps:gridfield>
				<reps:gridfield title="奖励/扣除积分" width="15" align="center">
					<c:if test="${data.study.mark=='1'}">
						<c:if test = "${data.study.pointsScope=='5'}">
							+1,+2,+3,+4,+5
						</c:if>
						<c:if test = "${data.study.pointsScope=='4'}">
							+1,+2,+3,+4
						</c:if>
						<c:if test = "${data.study.pointsScope=='3'}">
							+1,+2,+3
						</c:if>
						<c:if test = "${data.study.pointsScope=='2'}">
							+1,+2
						</c:if>
						<c:if test = "${data.study.pointsScope=='1'}">
							+1
						</c:if>
					</c:if>
					<c:if test="${data.study.mark=='0'}">
						<c:if test = "${data.study.pointsScope=='5'}">
							-1,-2,-3,-4,-5
						</c:if>
						<c:if test = "${data.study.pointsScope=='4'}">
							-1,-2,-3,-4
						</c:if>
						<c:if test = "${data.study.pointsScope=='3'}">
							-1,-2,-3
						</c:if>
						<c:if test = "${data.study.pointsScope=='2'}">
							-1,-2
						</c:if>
						<c:if test = "${data.study.pointsScope=='1'}">
							-1
						</c:if>
					</c:if>
				</reps:gridfield>
				<reps:gridfield title="操作" width="18">
					<reps:ajax id="delete"  url="delrule.mvc?id=${data.id}" value="删除"
							cssClass="delete-table" confirm="你确定要删除吗？" redirect="fplist.mvc?behaviorId=${behaviorId}"></reps:ajax>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">

	var userCallBack = function(id,name) {
		$.ajax({
			url : "saverule.mvc",
			type : "POST",
			dataType : "json",
			data : {
				"teacherId" : id,
				"behaviorId" : $("#behaviorId").val()
			},
			async : false,
			success : function(data) {
				messager.message(data, function() {
					window.location.href = "fplist.mvc?behaviorId=${behaviorId}";
				});
			}
		});
	};
 	
</script>
</body>
</html>