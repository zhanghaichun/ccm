<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <title>Dashboard List</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Dashboard List">

    <!-- Bootstrap -->
    <link href="include/css/dashboard/css/dashboard_bootstrap.css" rel="stylesheet">
	<link href="include/css/dashboard/css/dashboard_common.css" rel="stylesheet">
    <link href="include/css/dashboard/css/dashboard_list.css" rel="stylesheet">


    <script src="include/javascript/jquery/jquery-1.4.2.min.js"></script>
    <script src="include/javascript/dashboard/dashboard_list.js"></script>
</head>
<body>
<div id="pageContainer" style="padding-bottom: 25px;">
	<main class="dashboard-list-main-container system-theme">
		<!-- 头部信息。 -->
		<header>
			<div class="container-fluid">
				<div class="page-title">
					<div class="row">
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 page-title-col-wrapper">
							<h3 class="title-content">Dashboard List</h3>
						</div>
						<div class="col-lg-6 col-md-6 col-sm-6 col-xs-6 add-new-col-wrapper">
							<div class="new-link-container text-right">
								<a  href="addNewDashboard.action?isMobile=${isMobile}" class="add-new-link" data-isMobile="${isMobile}">
									<input type="button" value="Add New Dashboard" class="add-new-button">
								</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</header>
		
		<!-- Dashboard 列表。 -->
		<section class="dashboard-list-section system-theme">
			<c:if test="${ not empty dashboardListInfo}">
				<!-- 列表标题。 -->
				<div class="list-title-wrapper">
					<div class="container-fluid">
						<div class="row">
							<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6 sort-list-header-wrapper">
								<div class="list-header">
									<span class="header-name">Dashboard Name</span>
									<i class="sort-icon" data-controller="dashboard-name"></i>
									<input id="dashboard-name" type="hidden" value="" >
								</div>
								
							</div>
							<div class="col-lg-4 col-md-4 col-sm-4 hidden-xs sort-list-header-wrapper">
								<div class="list-header text-center">
									<span class="header-name">Date Created</span>
									<i class="sort-icon" data-controller="date-created"></i>
									<input id="date-created" type="hidden" value="" >
								</div>
								
							</div>
							<div class="col-lg-4 col-md-4 col-sm-4 col-xs-6">
								<div class="list-header text-right">
									<span class="header-name operation-header-name">Operation</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				
				<!-- 列表内容。 -->
				<div class="dashboard-list">
					<div class="dashboard-list-wrapper">
					<c:forEach items="${dashboardListInfo}" var="dashboardListInfo">
						<div class="dashboard-list-item border-box">
							<div class="container-fluid">
								<div class="list-item-content">
									<div class="row">
										<div class="col-lg-5 col-md-5 col-sm-5 col-xs-6">
											<div class="dashboard-name">
												<div class="name-content font-11">${dashboardListInfo.dashboardName}</div>
											</div>
				
											<div class="time-small">
												(<span class="date-time">${dashboardListInfo.createdTimestamp}</span>)
											</div>
										</div>
										<div class="col-lg-2 col-md-2 col-sm-2 hidden-xs">
											<div class="creation-date text-center font-11">${dashboardListInfo.createdTimestamp}</div>
										</div>
										<div class="col-lg-5 col-md-5 col-sm-5 col-xs-6">
											<div class="operation text-right">
												<!-- 删除dashboard list 中的某一项。 -->
												<a href="javascript:void(0);" onclick="deleteDashboardListItem(${dashboardListInfo.id}, this)" class="font-12 delete-button">
													<input type="button" value="Remove">
												</a>
												
												<!-- 进入显示该 dashboard 详细信息的页面。 -->
												<a href="showDashboardDetail.action?userDashboardId=${dashboardListInfo.id}&isMobile=${isMobile}" class="font-12 enter-button">
													<input type="button" value="Open">
												</a>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
				
					</c:forEach>
					</div>
				</div>
			</c:if>
			
			<c:if test="${ not empty dashboardListInfo}">
				<!-- 分页控件 -->
				<div class="pagination-control">
					<img class="pagination-image" src="include/images/button_previous_first.gif" title="First Page" onclick="backwardFirstPage()">
					<img class="pagination-image" src="include/images/button_previous.gif" title="Previous Page" onclick="backwardPreviousPage()">
					
					<span>Page</span>
					<input class="current-page" type="text" onkeydown="renderDashboardListByCurrentPageCount(this.value)" value="1">
					<span>of</span>
					<span class="total-page">0</span>
				
					<img class="pagination-image" src="include/images/button_next.gif" title="Next Page" onclick="ForwardNextPage()">
					<img class="pagination-image" src="include/images/button_next_last.gif" title="Last Page" onclick="ForwardLastPage()">
					
					<!-- 每一页应该显示的条数 -->
					<select name="recordsCount" id="recordsCount" onchange="renderDashboardListByPerPageCount()">
						<option value="5">5</option>
						<option value="10">10</option>
						<option value="15">15</option>
					</select>
					<!-- 记录的总条数。 -->
					<input id="totalRecordsCount" type="hidden" value="0">
				
					<span class="total">
						<span class="title-name">Total Items: </span>
						<span class="total-number"></span>
					</span>
				</div>
			</c:if>
			<div style="margin-top: 20px;"></div>
		</section>
	</main>

	
</div>
</body>
</html>