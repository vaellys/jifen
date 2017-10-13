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
	<reps:panel id="mytop" dock="top" action="${ctx}/reps/jifen/pjfzsz/xxlist.mvc" method="post" formId="queryForm">
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
			<reps:button cssClass="add-a" action="toadd.mvc?category=${query.category}" value="新增"></reps:button>
			<!-- <reps:ajax id="delete" cssClass="delete-a" beforeCall="deleteChecked" formId="queryForm"  value="批量删除" confirm="你确定要删除吗？" redirect="xxlist.mvc"></reps:ajax> -->
		</reps:footbar>
	</reps:panel>
	<reps:panel id="mybody" dock="center">
		<reps:grid id="mygrid" items="${list}" var="data" form="queryForm" flagSeq="false" pagination="${pager}">
			<reps:gridrow>
				<reps:gridcheckboxfield checkboxName="id" align="center" title="" width="5">${data.id}</reps:gridcheckboxfield>
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
					<reps:button cssClass="modify-table" action="toedit.mvc?id=${data.id}" value="修改"></reps:button>
					<reps:ajax id="delete"  url="delete.mvc?ids=${data.id}" value="删除"
							cssClass="delete-table" confirm="你确定要删除吗？" redirect="xxlist.mvc"></reps:ajax>
					<c:if test="${data.isEnable=='0'}">
						<reps:ajax  cssClass="start-use-table" url="isenable.mvc?id=${data.id}&isEnable=1" confirm="你确定要启用吗？" redirect="xxlist.mvc" value="启用"></reps:ajax>
					</c:if>
					<c:if test="${data.isEnable=='1'}">
						<reps:ajax  cssClass="stop-use-table" url="isenable.mvc?id=${data.id}&isEnable=0" confirm="你确定要禁用吗？" redirect="xxlist.mvc" value="禁用"></reps:ajax>
					</c:if>
				</reps:gridfield>
			</reps:gridrow>
		</reps:grid>
	</reps:panel>
</reps:container>
<script type="text/javascript">

	function deleteChecked() {
		var ids = $("input[type=hidden][name=ids]");
		ids.val("");
		$.each($("input[type=checkbox][name=id]:checked"), function(i, obj) {
			if (ids.val() == "") {
				ids.val($(obj).val());
			} else {
				ids.val(ids.val() + "," + $(obj).val());
			}
		});
		if (ids.val() == '') {
			messager.info('请先选择要删除的数据', {
 					okCall:function(){
 					}
 			});
			return false;
		}
		$("#queryForm").attr("action", "${ctx}/reps/jifen/pjfzsz/delete.mvc");
		return true;
	}
 	
</script>
</body>
</html>