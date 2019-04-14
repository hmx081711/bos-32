<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>tabs</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/js/ztree/zTreeStyle.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/ztree/jquery.ztree.all-3.5.js"></script>

</head>
<body class="easyui-layout">
	<!-- 使用div元素描述每个区域 -->
	<div title="XXX管理系统" style="height: 100px" data-options="region:'north'">北部区域</div>
	<div title="系统菜单" style="width: 200px" data-options="region:'west'">
		<!-- 制作accordion折叠面板 
			fit:true----自适应(填充父容器)
		-->
		<div class="easyui-accordion" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			<div data-options="iconCls:'icon-cut'" title="面板一"><a id="addtabs" class="easyui-linkbutton">添加一个选项卡</a></div>
			<script type="text/javascript">
				$(function(){
					$("#addtabs").click(function(){
						
						
						var e = $("#mytabs").tabs("exists","管理系统");
						if(e) {
							$("#mytabs").tabs("select","管理系统");
						}else {
							$("#mytabs").tabs("add",{
								title:'管理系统',
								iconCls:'icon-cut',
								closable:true,
								content:'<iframe frameborder="0" src="https://baidu.com" style="height: 100%;width: 100%"></iframe>'
							});
							}
						});
				});
			</script>
			<div title="面板二">
				<ul id="tree1" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						var setting = {};
						var zTreeNodes = [{"name":"节点一","children":[{"name":"节点四"}]},
									  {"name":"节点二"}
						             ,{"name":"节点三"}];
						
						zTreeObj = $.fn.zTree.init($("#tree1"), setting, zTreeNodes);
					})                        
				</script>
			</div>
			<div title="面板三">
				<ul id="tree2" class="ztree"></ul>
				<script type="text/javascript">
					$(function(){
						// 通过ajax获取ztree的数据
						var url = "${pageContext.request.contextPath}/json/menu.json";
						//使用简单json构造树
						var setting2 = {
								data: {
									simpleData: {
										enable: true,
									}
								},
						
								callback: {
									onClick: function(event,treeId,treeNode){				
										//为选项卡添加新页面
										//判断节点类型
										var e = $("#mytabs").tabs("exists",treeNode.name);
										if(e){
											//存在
											$("#mytabs").tabs("select",treeNode.name);
										}else{
											//不存在
											if(treeNode.page != undefined){					
										$("#mytabs").tabs("add",{
											title:treeNode.name,
											closable:true,
											content:'<iframe frameborder="0" height="100%" width="100%" src="${pageContext.request.contextPath}/'+treeNode.page+'"></iframe>'
										})
											}
										}
									}
									}
						};
						$.post(url,{},function(data){
							var ztreeNodes = [{"id":"1","pId":"0","name":"节点一"},
											  {"id":"2","pId":"1","name":"节点二"},
											  {"id":"3","pId":"2","name":"节点三"}];
							zTreeObj2 = $.fn.zTree.init($("#tree2"), setting2, data);
						},"json");
						
						
					});               	
				</script>
			</div>
		</div>
	</div>
	<div data-options="region:'center'">
		<!-- 制作一个tabs选项卡面板 -->
		<div id="mytabs" class="easyui-tabs" data-options="fit:true">
			<!-- 使用子div表示每个面板 -->
			<div data-options="iconCls:'icon-cut'" title="面板一">1111</div>
			<div data-options="closable:true" title="面板二">2222</div>
			<div title="面板三">3333</div>
		</div>
	</div>
	<div style="width: 100px" data-options="region:'east'">东部区域</div>
	<div style="height: 50px" data-options="region:'south'">南部区域</div>
</body>
</html>