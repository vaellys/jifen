<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/commons/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>导入展示页</title>
	<reps:theme />
</head>
<body>
<reps:container layout="true">
	<reps:panel id="mytop" dock="top" action="${ctx}/reps/jifen/pjfzsz/xyxzlist.mvc" method="post" formId="queryForm">
		<input type="hidden" name="ids">
		<reps:formcontent parentLayout="true" style="width:80%;">
			<reps:formfield label="指标项名称" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:input name="item">${query.item}</reps:input>
			</reps:formfield>
			<reps:formfield label="指标类型" labelStyle="width:20%;" textStyle="width:27%;">
				<reps:select name="mark" jsonData="{'':'', '0':'扣除', '1':'奖励'}">${query.mark}</reps:select>
			</reps:formfield>
		</reps:formcontent>
		<reps:querybuttons>
			<reps:ajaxgrid messageCode="manage.button.query" formId="queryForm"  gridId="mygrid" callBack="setDisabled" cssClass="search-form-a"></reps:ajaxgrid>
		</reps:querybuttons>
		<reps:footbar>
			 <!--<reps:ajax id="delete" cssClass="delete-a" beforeCall="deleteChecked" formId="queryForm"  value="批量删除" confirm="你确定要删除吗？" redirect="xylist.mvc"></reps:ajax>  -->  
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="mygrid" items="${list}" var="data" form="queryForm" flagSeq="false" pagination="${pager}">
			<reps:gridrow>
				<reps:gridfield title="指标项" width="15" align="center">${data.item}</reps:gridfield>
				<reps:gridfield title="指标类型" width="15" align="center">
					<c:if test="${data.mark=='0'}">扣除</c:if>
					<c:if test="${data.mark=='1'}">奖励</c:if>
				</reps:gridfield>
				<reps:gridfield title="奖励/扣除积分" width="15" align="center">
					<c:if test="${data.mark=='1'}">
						<c:if test = "${data.pointsScope=='5'}">
							+1,+2,+3,+4,+5
						</c:if>
						<c:if test = "${data.pointsScope=='4'}">
							+1,+2,+3,+4
						</c:if>
						<c:if test = "${data.pointsScope=='3'}">
							+1,+2,+3
						</c:if>
						<c:if test = "${data.pointsScope=='2'}">
							+1,+2
						</c:if>
						<c:if test = "${data.pointsScope=='1'}">
							+1
						</c:if>
					</c:if>
					<c:if test="${data.mark=='0'}">
						<c:if test = "${data.pointsScope=='5'}">
							-1,-2,-3,-4,-5
						</c:if>
						<c:if test = "${data.pointsScope=='4'}">
							-1,-2,-3,-4
						</c:if>
						<c:if test = "${data.pointsScope=='3'}">
							-1,-2,-3
						</c:if>
						<c:if test = "${data.pointsScope=='2'}">
							-1,-2
						</c:if>
						<c:if test = "${data.pointsScope=='1'}">
							-1
						</c:if>
					</c:if>
				</reps:gridfield>
				<reps:gridfield title="是否可用" width="9" align="center">
					<c:if test="${data.isEnable=='0'}">否</c:if>
					<c:if test="${data.isEnable=='1'}">是</c:if>
				</reps:gridfield>
				<reps:gridfield title="操作" width="18">
					<reps:button  cssClass="modify-table" action="fplist.mvc?behaviorId=${data.id}" value="教师分配" />
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">

</script>
</body>
</html>