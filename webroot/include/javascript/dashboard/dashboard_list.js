

// 声明一个全局变量用来记录，
// 当前页面状态是否属于移动端的页面。
var isMobileState = false;
$(function() {
	
	// 获取数据库中当前用户的 dashboard list item 的总条数。
	getTotalRecordsNo();

	// 切换升序和降序排列的图标。
	switchAscOrDesc();

	// 判断当前是否处于手机模式下.
	verifyCurrentDeviceState();

});

/**
 * 确认当期的设备的状态。
 */
function verifyCurrentDeviceState() {
	var deviceState = $('.add-new-link').attr('data-isMobile');
	
	if ( deviceState == 'true' ) {
		isMobileState = true
	} else {
		isMobileState = false
	}
}

/**
 * 首先获取数据库中 dashboard List 的总的条目数。
 * 并在页面中显示出来总条数。
 */
function getTotalRecordsNo() {
	
	$.ajax({
		url: "getTotalRecordsNo.action",
		type: "POST",
		dataType: "json",
		success: function(count) {

			// 如果后台中返回的总的 dashboard
			// list item的条目是0， 那么就显示提示信息
			if ( count.totalCount == 0 ) {
				
				renderPromptWithoutListItem();
				return false;
			}
			
			// 设置总条数。
			$('#totalRecordsCount').val(count.totalCount);

			// 在页面中显示总条数。
			$('.total-number').text(count.totalCount);

			
			// 初始化总页数。
			initTotalPageNo();
		},
		error: function() {
			alert("Request total records number failed!");
		}
		
	});
	
	
}

/**
 * 动态绘制当没有列表项的时候的显示结构。
 */
function renderPromptWithoutListItem() {
	$('.list-title-wrapper').remove();
	$('.dashboard-list').remove();
	$('.pagination-control').remove();

	var emptyListStructure = '';
	emptyListStructure += '<div class="empty-list">';
	emptyListStructure += '<span class="prompt-message">Welcome to Dashboard. Get started by clicking </span>';
	emptyListStructure += '<a href="addNewDashboard.action?isMobile='+ isMobileState +'" class="empty-add-new">Add New Dashboard!</a>';
	emptyListStructure += '</div>';

	$('.dashboard-list-section').append( emptyListStructure  );
}

/**
 * 删除 dashboard list 列表项。
 * 同时要保持分页和排序的显示正常。
 * @return {[type]} [description]
 */
function deleteDashboardListItem(userDashboardId, self) {
	if(confirm('Are you sure you want to remove this dashboard?')) {
		$.ajax({
			url: "deleteDashboardListItem.action",
			type: "POST",
			dataType: "text",
			data: {userDashboardId: userDashboardId},
			success: function(o) {

				$(self).parents('.dashboard-list-item').remove();
				getTotalRecordsNo();

				pagingShowWhenDelete();

				searchDataByConditions();
			},
			error: function() {}
		});
	}
}

/**
 * Dashboard list item 分页显示的时候，每一页显示项目的条数固定，
 * 当把这一页中的项目条数删除到0
 * 的时候，会自动返回上一页来显示列表，其他信息也会改变。
 */
function pagingShowWhenDelete() {

	var dashboardListLen = $('.dashboard-list-item').length;
	var currentPage = parseInt( $('.current-page').val() );

	if ( dashboardListLen == 0 && ( currentPage > 1 ) ) {
		$('.current-page').val( currentPage - 1 );
	}
}


/**
 * Dashboard List 列表的升序和降序的排序图标的替换动作。
 * 包括向后台请求数据的动作。
 * 现在后台中都是按照一种字段来进行排序的。
 * （dashboard name 或者是 date created）
 */
function switchAscOrDesc() {
	$('.sort-icon').click(function() {

		// 前台排序图标标志的显示。
		if ( !$(this).hasClass('asc') ) {
			$(this).addClass('asc').removeClass('desc');
		} else {
			$(this).removeClass('asc').addClass('desc');
		}

		// 设置向后台排序的种类的值。
		if ( $(this).hasClass('desc') ) {
			$('#' + $(this).attr('data-controller')).val('DESC');
		} else {
			$('#' + $(this).attr('data-controller')).val('ASC');
		}

		// 在前台中， 点击激活一个字段的排序，另一个字段的
		// 状态还原。
		$(this).parents('.sort-list-header-wrapper').siblings()
			.find('.sort-icon').attr('class', '').addClass('sort-icon');

		$(this).parents('.sort-list-header-wrapper').siblings()
			.find('input').val('');

		
		// 按条件请求数据。
		searchDataByConditions();

	});
}


/**
 * 按条件请求数据。
 * 这个方法是为了分页和排序的时候的ajax请求。
 * @return
 */
function searchDataByConditions() {
	var dashboardNameSort = $('#dashboard-name').val();
	var dateCreatedSort = $('#date-created').val();
	var currentPage = $('.current-page').val();
	var pageRecords = $('#recordsCount').val();
	
	var requestData = {
		dashboardNameSort: dashboardNameSort,
		dateCreatedSort: dateCreatedSort,
		currentPage: currentPage,
		pageRecords: pageRecords,
	};
	
	$.ajax({
		url: "searchDashboardListByConditions.action",
		type: "POST",
		async: true,
		dataType: "json",
		data: requestData,
		success: function(o) { // 返回的是一个数组。
			renderDashboardList(o);
		}
	});
}

/**
 * 接收的参数是从后台中返回的JSON数据， 是一个数组，
 * 数组中的每一个对象中的数据就是渲染列表中每一个条目所需要的数据。
 * @param  {[type]} dashboardListJSONArray [description]
 * @return {[type]}                        [description]
 */
function renderDashboardList( dashboardListJSONArray ) {

	var dashboardListItemStructure = '';
	for(var i = 0, len = dashboardListJSONArray.length; i < len; i ++) {

		dashboardListItemStructure += '<div class="dashboard-list-item border-box"><div class="container-fluid">';
		dashboardListItemStructure += '<div class="list-item-content"><div class="row"><div class="col-lg-5 col-md-5 col-sm-5 col-xs-6">';
		dashboardListItemStructure += '<div class="dashboard-name"><i class="name-icon"></i>';
		dashboardListItemStructure += '<div class="name-content font-11">'+ dashboardListJSONArray[i].dashboard_name +'</div>';
		dashboardListItemStructure += '</div><div class="time-small">';
		dashboardListItemStructure += '(<span class="date-time">'+ dashboardListJSONArray[i].created_timestamp +'</span>)';
		dashboardListItemStructure += '</div></div><div class="col-lg-2 col-md-2 col-sm-2 hidden-xs">';
		dashboardListItemStructure += '<div class="creation-date text-center font-11">'+ dashboardListJSONArray[i].created_timestamp +'</div>';
		dashboardListItemStructure += '</div><div class="col-lg-5 col-md-5 col-sm-5 col-xs-6"><div class="operation text-right">';
		dashboardListItemStructure += '<a href="javascript:void(0);" onclick="deleteDashboardListItem('+ dashboardListJSONArray[i].id +', this)" class="font-12 delete-button"><input type="button" value="Remove"></a> ';
		dashboardListItemStructure += ' <a href="showDashboardDetail.action?userDashboardId='+ dashboardListJSONArray[i].id +'&isMobile='+ isMobileState +'" class="font-12 enter-button"><input type="button" value="Open"></a>';
		dashboardListItemStructure += '</div></div></div></div></div></div>';
	}

	// .dashboard-list-wrapper
	$('.dashboard-list-wrapper').html(dashboardListItemStructure);

}

/**
 * 获得dashboard list 中分页的总页数。
 * @return {Number} dashboard list 中分页的总页数。
 */
function getTotalPagesNo() {
	// 数据总条数
	var totalRecordsCount = $('#totalRecordsCount').val();

	// 每一页显示的条数。
	var perPageCount = $('#recordsCount').val();

	var totalPages = Math.ceil( totalRecordsCount/perPageCount );

	return totalPages;
}

/**
 * 初始化记录分页的页数。根据的是list item 的总条数除以每一页
 * 显示的条数。
 */
function initTotalPageNo() {

	$('.total-page').text( getTotalPagesNo() );
	
}

/**
 * 根据每一页显示的条数来重新渲染 Dashboard List.
 * @return {[type]} [description]
 */
function renderDashboardListByPerPageCount() {

	// 首先需要改变分页的页数。
	initTotalPageNo();
	$('.current-page').val(1); // 初始化的时候重置当前的页数。
	searchDataByConditions();

}

/**
 * 当使用键盘上的按键输入页数，之后点击enter键的视时候
 * 可以进行相应页面的数据的搜索。
 * @return
 */
function renderDashboardListByCurrentPageCount(currentPageValue) {
	
	// 确保输入的是数组的正则表达式
	var numberRegExp = /^[1-9]+[0-9]*]*$/;
	
	if ( window.event.keyCode == 13 ) {
		if (currentPageValue <= getTotalPagesNo() && currentPageValue > 0 && numberRegExp.test(currentPageValue) ) {
			
			// 在这个判断中请求数据。
			searchDataByConditions();
		} else {
			alert("Please enter a correct page number.");
		}
		
	}
}

/**
 * 索引到第一页。
 */
function backwardFirstPage() {
	var firstPageNo = 1; 
	// 退回到第一页。
	$('.current-page').val(firstPageNo);

	searchDataByConditions();
}

/**
 * 索引到前一页。
 */
function backwardPreviousPage() {

	var currentValue = parseInt($('.current-page').val());
	// 退回到前一页
	if (currentValue > 1) { // 对页数的最小值进行限制。
		$('.current-page').val(currentValue - 1);
	}

	searchDataByConditions();
	

}

/**
 * 索引到下一页。
 */
function ForwardNextPage() {
	var currentValue = parseInt($('.current-page').val());
	var totalPages = getTotalPagesNo();

	// 进入到下一页
	if (currentValue < totalPages) { // 对页数的最大值进行限制。
		$('.current-page').val(currentValue + 1);
	}

	searchDataByConditions();
}

/**
 * 索引到最后一页。
 */
function ForwardLastPage() {
	$('.current-page').val( getTotalPagesNo() );

	searchDataByConditions();
}
