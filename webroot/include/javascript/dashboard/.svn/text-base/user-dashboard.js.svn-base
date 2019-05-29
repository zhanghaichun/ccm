(function() {
	if (typeof (saninco) == "undefined")
		saninco = {};
	saninco.Dashboard = function(options) {
		opts = $.extend({
			element : 'dashboard',
			data:{}
		}, options);
		DIV = function(style) {
			return $('<div>').addClass(style);
		};
		
		var p = {
			control : {},
			items: [],
			itemContainers: [],
			init : function() {
				p.data = opts.data;
				p.control = $('#'+opts.element) || $('<div>').appendTo($(document.body));
				
				if (p.control) {
					p._createHeader();
					p._createDashboards();
					p._bindHideEvent();
				}
			},
			
			_createHeader: function() {
				
				p.headerDiv = DIV('row');
				DIV('container-fluid').append(p.headerDiv).appendTo(p.control);
				p._createTitle();
				p._createGlobalFilter();
			},

			/**
			 * 创建 Dashboard Title. 
			 * @return {[type]} [description]
			 */
			_createTitle: function() {
				
				p.titleParent = DIV('edit-dashboard-name-container');
				DIV('col-md-6 col-sm-6 col-xs-12').append(p.titleParent).appendTo(p.headerDiv);
				p.titleContent = DIV('row');
				DIV('container-fluid').append(p.titleContent).appendTo(p.titleParent);
				p._createTitleEditor();
			},
			_getFilterOptionsByCanUseConditionKey: function(key) {
				var options = {isShowConfirm: true, data: []},canUseConditions = [];
				switch (key) {
				case "timePeriod" :
					options.isShowConfirm = false;
					canUseConditions.push({key: 'timePeriod', condition: p.data.timePeriodListGlobal});
					break;
				case "vendor" :
					canUseConditions.push({key: 'vendor', isMultiple: true, condition: p.data.vendorList});
					break;
				case "product" :
					canUseConditions.push({key: 'product', title:'Product', isMultiple: true, condition: p.data.productList});
					canUseConditions.push({key: 'productLabel', title: 'Product Label', condition: p.data.productLabelList});
					break;
				case "region" :
					canUseConditions.push({key: 'region', isMultiple: true, condition: p.data.provinceList});
					break;
				case "budget" :
					canUseConditions.push({key: 'budget', title:'Budget', isMultiple: true, condition: p.data.budgetList});
					canUseConditions.push({key: 'budgetOwner', title: 'Budget Owner', condition: p.data.budgetOwnerList});
					break;
				case "cto" :
					canUseConditions.push({key: 'cto', isMultiple: true, condition: p.data.auditReferenceList});
					break;
				case "quoteProduct" :
					canUseConditions.push({key: 'quoteProductLabel', title: 'Product Label', condition: p.data.quoteProductLabelList});
					canUseConditions.push({key: 'quoteProduct', title:'Product', isMultiple: true, condition: p.data.quoteProductList});
					break;
				case "quoteVendor" :
					canUseConditions.push({key: 'quoteVendor', isMultiple: true, condition: p.data.quoteVendorList});
					break;
				case "quoteNetType" :
					canUseConditions.push({key: 'quoteNetType', isMultiple: true, condition: p.data.quoteNetTypeList});
					break;
				}
				
				for (var i = 0, length = canUseConditions.length; i < length; i++) {
					var conditionData = {}, canUseCondition = canUseConditions[i], conditions = [];
					conditionData.key = canUseCondition.key;
					if (canUseCondition.isMultiple)
						conditionData.isMultiple = canUseCondition.isMultiple;
					if (canUseCondition.title)
						conditionData.title = canUseCondition.title;
					for (var j = 0, jLength = canUseCondition.condition.length; j < jLength; j++) {
						var key = (canUseCondition.condition[j] && canUseCondition.condition[j].id != undefined ? canUseCondition.condition[j].id : canUseCondition.condition[j]),
							name = (canUseCondition.condition[j] && canUseCondition.condition[j].labelName ? canUseCondition.condition[j].labelName : canUseCondition.condition[j]);
						var condition = {key: key, name: name};
						conditions.push(condition);
					}
					conditionData.condition = conditions;
					options.data.push(conditionData);
				}
					
				return options;
			},
			_createGlobalFilter: function() {
				var globalDashboardControlList = p.data.globalDashboardControlList, dashboardControl, controlName;
				
				p.globalFilterParent = DIV('global-controller-dropdown').hide();
				DIV('col-md-4 col-md-offset-2 col-sm-4 col-sm-offset-2 col-xs-12').append(p.globalFilterParent).appendTo(p.headerDiv);
				p.globalFilterTitle = DIV('global-controller-dropdown-title').appendTo(p.globalFilterParent).click(function(e) {
					p.globalFilterTitleText.toggleClass('foldup');
					p.globalFilterTitle.toggleClass('border-bottom-none');
					p.globalFilter.toggle();
					
					if (saninco.dashboardFilter) {
						saninco.dashboardFilter.closeFilter();
					}
					
					if (e.stopPropagation)
						e.stopPropagation();
					else
						e.cancelBubble = true;
				});
				p.globalFilterTitleText = DIV('select-title foldup pointer font-600 select-none').append('Global Control').appendTo(p.globalFilterTitle);
				p.globalFilter = DIV('global-controller-list-container').hide().appendTo(p.globalFilterParent)
					.click(function(e) {
						if (e.stopPropagation)
							e.stopPropagation();
						else
							e.cancelBubble = true;
					});
				p.globalFilterUl = $('<ul class="global-controller-list">').appendTo(p.globalFilter);
				for (var i = 0, length = globalDashboardControlList.length; i < length; i++) {
					dashboardControl = globalDashboardControlList[i];
					$('<li class="global-controller-list-item is-modul-filter" control-key="' + dashboardControl.controlKey + '" is-show-filter="N" >').click(function(e) {
						var opts = p._getFilterOptionsByCanUseConditionKey($(this).attr('control-key')),
						target = $(this).find('.edit-icon.icon'), offset = target.offset(),
						position = {x: offset.left, y: offset.top};
						
						opts.callback = p._submitAll;
						if (!saninco.dashboardFilter) {
							saninco.dashboardFilter = new saninco.DashboardModuleFilter();
						} 
						
						if ($(this).attr('is-show-filter') == "N") {
							saninco.dashboardFilter.openFilter(position, opts);
							$(document).find('.is-modul-filter').attr('is-show-filter', 'N');
							$(this).attr('is-show-filter','Y');
						} else {
							saninco.dashboardFilter.closeFilter();
							$(this).attr('is-show-filter','N');
						}
						
						
						if (e.stopPropagation)
							e.stopPropagation();
						else
							e.cancelBubble = true;
					}).append(
						DIV('item-wrapper').append(
							DIV('item-content').append(
								DIV('filter-name-wrapper').append(DIV('filter-name').append(dashboardControl.globalControlName))
							).append(
								DIV('edit-icon-wrapper').append($('<i class="edit-icon icon" >'))
							)
						)
					).appendTo(p.globalFilterUl);
				}
			},
			_submitAll: function(data) {
				var item;
				for (var i = 0; i < p.items.length; i++) {
					item = p.items[i];
					item.submit(data);
				}
			},
			_createTitleEditor: function() {
				
				p.titleTextContent = DIV('col-md-8 col-sm-8 col-xs-8 edit-dashboard-name-column').appendTo(p.titleContent);
				p.titleInputContent = DIV('non-edit clearfix').appendTo(p.titleTextContent);
				p.titleText = DIV('title-tontant font-18 font-bold').click(function() {
					p.titleInputEditBtn.hide();
					p.titleInput.show();
					p.titleInput.focus();
					p.titleInputContent.removeClass('non-edit').addClass('editing');
					p.titleBtnCancel.show();
					p.titleBtnConfirm.show();
					p.titleText.hide();
				}).appendTo(p.titleInputContent);
				p.titleInput = $('<input class="font-18 font-bold no-border edit-textbox" type="text" />')
					.bind('keydown', function(event) {
						
						if( event.keyCode == 13) {
							p._saveTitle();
						}
					}).hide().appendTo(p.titleInputContent);
				p.titleButtonsContent = DIV('col-md-4 col-sm-4 col-xs-4 edit-dashboard-name-oper-buttons-column').appendTo(p.titleContent);
				p.titleButtons = DIV('edit-oper-buttons').appendTo(p.titleButtonsContent);
				p.titleBtnConfirm = $('<a href="javascript:void(0)" class="btn db-button-primary font-12 narrow-button confirm-button themes-confirm">Confirm</a>').appendTo(p.titleButtons)
					.click(function(e) { // 确认修改 Dashboard Name 动作的按钮。
						
						p._saveTitle();
					}).hide();
				p.titleBtnCancel = $('<a href="javascript:void(0)" class="btn db-button-default font-12 narrow-button cancel-button themes-cancel">Cancel</a>').appendTo(p.titleButtons)
					.click(function(e) { // 取消修改 Dashboard Name 动作的按钮。
						p.titleInputEditBtn.show();
						p.titleText.show();
						p.titleInput.hide();
						p.titleInputContent.removeClass('editing').addClass('non-edit');
						p.titleBtnCancel.hide();
						p.titleBtnConfirm.hide();
						p.setTitle(p.data.dashboardName);
					}).hide();

				p.titleInputEditBtn = $('<i class="edit-name-icon pull-right icon">').appendTo(p.titleInputContent)
					.click(function(e) { // 点击 icon 编辑 dashboard Name.
						p.titleInputEditBtn.hide();
						p.titleInput.show();
						p.titleInput.focus();
						p.titleInputContent.removeClass('non-edit').addClass('editing');
						p.titleBtnCancel.show();
						p.titleBtnConfirm.show();
						p.titleText.hide();
					});
				p.setTitle(p.data.dashboardName);
			},
			setTitle: function(title) {
				p.titleInput.val(title);
				p.titleText.html(title);
			},
			_createDashboards: function() {
				var moduleData = {};
				p.dashboardDiv = DIV('dashboard-dashboards').appendTo(p.control);
				itemDatas = p.data.userDashboardModuleList;

				// 循环渲染数据可视化Panels( Dashboard Panels ).
				for (var i = 0; i < itemDatas.length; i++) {
					var data = itemDatas[i];
					var item = p._createDashboardModule(data);
				}
				if (p.items.length > 0)
					p.globalFilterParent.show();
				else
					p.globalFilterParent.hide();
				// 下面的这个面板不是正常的可视化数据的面板，
				// 而是一个用来添加新Dashboard Panel 的控件。
				p._createModuleSelect(); 
				p.addNewDiv = DIV('add-new-wrapper')
							.append(DIV('add-new-container')
									.append(DIV('add-new')
											.append($('<i class="add-new-icon icon"></i>'))
											.append(DIV('add-new-content').append('New Dashboard'))
											)
									).click(function(e) {
										p.addNewDiv.hide();
										p.moduleSelectDiv.show();
									});
				DIV('col-md-6 dashboard-default').append(p.addNewDiv).append(p.moduleSelectDiv).appendTo(p.control);
			},
			_saveTitle: function() {
				
				var value = $.trim(p.titleInput.val()), message;
				if (value.length > 40) {
					message = "Dashboard name can't be greater than 40 characters.";
				}
				if (value.length == 0) {
					message = "Dashboard name can't be empty.";
				}
				if (message) {
					alert(message);
					return;
				}
				$.ajax({
			        url: "updateUserDashboardName.action",
			        type: "POST",
			        dataType: "json",
			        data: {"userDashboardId": p.data.id, "userDashboardName": value},
			        success: function(o){
			        	if (o.data == "sameName") {
			        		alert("The dashboard name you entered already exists, please choose another one.");
			        	} else if (o.data == "success") {
			        		p.titleInputEditBtn.show();
							p.titleText.show();
							p.titleInput.hide();
							p.titleInputContent.removeClass('editing').addClass('non-edit');
							p.titleBtnCancel.hide();
							p.titleBtnConfirm.hide();
							p.data.dashboardName = p.titleInput.val();
							p.setTitle(p.data.dashboardName);
			        	}
			        },
					error : p._showError
			     });
			},
			_moveModule: function(moduleContent, userDashboardModuleId) {
				
				return function(i) {
					 
					var item, itemContainer, index = $.inArray(moduleContent, p.itemContainers), newIndex = index+i;
					if (newIndex >= 0 && newIndex < p.itemContainers.length) {
						
						$.ajax({
					        url: "updateUserDashboardModuleMoveNewSort.action",
					        type: "POST",
					        dataType: "json",
					        data: {"userDashboardModuleId": userDashboardModuleId, "sortNo" : newIndex+1},
					        success: function(o){
					        	itemContainer = p.itemContainers[index];
								p.itemContainers[index] = p.itemContainers[newIndex];
								p.itemContainers[newIndex] = itemContainer;
								for (var i = 0; i < p.itemContainers.length; i++) {
									p.dashboardDiv.append(p.itemContainers[i]);
								}
								item = p.items[index];
								p.items[index] = p.items[newIndex];
								p.items[newIndex] = item;
								p._setModulesMoveBtnVisable();
								if (p.items.length > 0)
									p.globalFilterParent.show();
								else
									p.globalFilterParent.hide();
					        },
							error : p._showError
					     });
						
					}
					
				}
			},
			_setModulesMoveBtnVisable: function() {
				for (var i = 0, length = p.items.length; i < length; i++) {
					var item = p.items[i];
					item.isShowLeftMoveBtn(true);
					item.isShowRightMoveBtn(true);
					if (i==0)
						item.isShowLeftMoveBtn(false);
					if (i==length-1)
						item.isShowRightMoveBtn(false);
					
				}
			},
			_createModuleSelect: function() {
				var dashboarModule;
				p.moduleSelectDiv = DIV('select-dashboard-type-panel').append('<div class="panel-title">Select Dashboard Type</div>').hide();
				p.moduleSelectContent = DIV('panel-content').appendTo(p.moduleSelectDiv);
				p.moduleSelectRadioContent = DIV('select-dashboard-module-radio-container dashboard-type-radio').appendTo(p.moduleSelectContent);
				for (var i = 0, length = p.data.dashboardModuleList.length; i < length; i++) {
					dashboarModule = p.data.dashboardModuleList[i];
					p.moduleSelectRadioContent.append($('<div class="radio-item"><input type="radio" name="dashboard-type" ' + (i==0 ? 'checked=true' : '') + ' module-id=' + 
							dashboarModule.id + '><i class="radio-button icon"></i><label>' + dashboarModule.moduleName + '</label></div>')
							.click(function() {
								$(this).find('input[type="radio"]').attr('checked', true);
								$(this).siblings().find('input[type="radio"]').removeAttr('checked');
							}));
				}
				DIV('button-container')
					.append($('<button class="btn db-button-default wide-button cancel-button themes-cancel">Cancel</button>')
							.click(function() {
								p.moduleSelectDiv.find('input[type="radio"]').removeAttr('checked');
								p.moduleSelectDiv.find('input[type="radio"]').first().attr('checked', true);
								p.addNewDiv.show();
								p.moduleSelectDiv.hide();
							}))
					.append($('<button class="btn db-button-primary wide-button confirm-button themes-confirm">Confirm</button>')
							.click(function() {
								var dashboardModuleId = p.moduleSelectRadioContent.find('input:checked').attr('module-id');
								if (dashboardModuleId) {
									$.ajax({
								        url: "addNewDashboarModule.action",
								        type: "POST",
								        dataType: "json",
								        data: {"userDashboardModuleId": p.data.id, "dashboardModuleId" : dashboardModuleId},
								        success: function(o){
								        	var module = p._createDashboardModule(o.data);
											module.showDropdownFilter();
											p.addNewDiv.show();
											p.moduleSelectDiv.hide();
											
											
											/* 判断当前客户端是手机还是PC
											   如果是手机，则需要将滚动条上移
											   两个 dashboard module 的高度 和 中间空白的距离*/

											// 如果客户端是手机
											// 而且宽度要小于容纳两个 dashboard module
											// 的时候。
											if ( !p._isPC() && window.innerWidth <= 992 ) { 

												$("body").scrollTop( document.body.offsetHeight - 1240 - 30 );
											}

								        },
										error : p._showError
								     });
								}
								
							}))
					.appendTo(p.moduleSelectContent);   
			},
			
			/**
			 * 判断当前客户端是手机还是PC
			 * @return {Boolean} [description]
			 */
			_isPC:function () {

				// 首先获取请求头信息 (Request Headers) 中的 user-Agent 信息。
			    var userAgentInfo = navigator.userAgent;

			    // 在移动设备的 user-Agent 信息中可能包含的关键词
			    var Agents = ["Android", "iPhone",
			                "SymbianOS", "Windows Phone",
			                "iPad", "iPod"];

			    // 方法返回值
			    var flag = true;

			    /* 遍历关键字判断 user-Agent 信息中是否含有某个关键字
			     如果包含某个关键字， 则返回标识 false, 代表此客户端是移动端*/
			    for (var v = 0; v < Agents.length; v++) {

			        if (userAgentInfo.indexOf(Agents[v]) > 0) {
			            flag = false;
			            break;
			        }
			    }

			    return flag;
			},
			_createDashboardModule: function(data) {
				var moduleContent = DIV('col-md-6 dashboard-default').attr('module-id', data.id).appendTo(p.dashboardDiv),
				options = data,
				module;
				options.vendorList = p.data.vendorList;
				options.timePeriodList = p.data.timePeriodList;
				options.timePeriodListGlobal = p.data.timePeriodListGlobal;
				options.timePeriodListQuote = p.data.timePeriodListQuote;
				options.productList = p.data.productList;
				options.productLabelList = p.data.productLabelList;
				options.provinceList = p.data.provinceList;
				options.auditReferenceList = p.data.auditReferenceList;
				options.budgetList = p.data.budgetList || [];
				options.budgetOwnerList = p.data.budgetOwnerList || [];
				options.quoteProductList = p.data.quoteProductList || [];
				options.quoteProductLabelList = p.data.quoteProductLabelList || [];
				options.quoteAgingList = p.data.quoteAgingList || [];
				options.quoteIssuedStatusList = p.data.quoteIssuedStatusList || [];
				options.quoteNetTypeList = p.data.quoteNetTypeList || [];
				options.quoteVendorList = p.data.quoteVendorList || [];
				options.quoteVendorResposeStatusList = p.data.quoteVendorResposeStatusList || [];
				
				module = new saninco.DashboardModule({element: moduleContent, data: options});
				module.setAfterRemove(function(module, moduleContent) {
					return function() {
						p.items.splice($.inArray(module, p.items), 1);
						p.itemContainers.splice($.inArray(moduleContent, p.itemContainers), 1);
						p._setModulesMoveBtnVisable();
						if (p.items.length > 0)
							p.globalFilterParent.show();
						else
							p.globalFilterParent.hide();
					}
				}(module, moduleContent));
				module.setMove(p._moveModule(moduleContent, data.id));
				p.items.push(module);
				p.itemContainers.push(moduleContent);
				p._setModulesMoveBtnVisable();
				if (p.items.length > 0)
					p.globalFilterParent.show();
				else
					p.globalFilterParent.hide();
				return module;
					
			},
			_bindHideEvent: function() {
				$(document).bind('click', function() {
					if ($('.list-vendor-container')) {
						$('.list-vendor-container').hide();
					}
					if ($('.global-controller-list-container')) {
						$('.global-controller-list-container').hide();
					}
				});
			},
		}
		this.setTitle = p.setTitle;
		this.control = p.control;
		return p.init.apply(p, arguments);
	};
	
	saninco.DashboardModule = function(options) {
		opts = $.extend({
			element: "dashboard-module",
			data:{},
			showLeftMoveBtnFlag: true,
			showRightMoveBtnFlag: true
		}, options);
		var controlData,
			vendors,
			timePeriods,
			products,
			productLabels,
			provinces,
			auditReferences,
			budgets,
			budgetOwners,
			quoteProducts,
			quoteProductLabels,
			quoteAgings,
			quoteIssuedStatus,
			quoteNetTypes,
			quoteVendors,
			quoteVendorResponseStatus;
		
		var DIV = function(style) {
			return $('<div>').addClass(style);
		};
		var p = {
			control : {},
			isDropdownFilterDivOpen : false,
			isShowValuePercent: false,
			showLeftMoveBtnFlag: true,
			showRightMoveBtnFlag: true,
			init : function() {
				p.data = opts.data;
				p._setIsShowValuePercent(p.data.showPercent);
				p.controlParent = typeof(opts.element) == "string" ? $('#'+opts.element) : opts.element;
				if (p.controlParent) {
					p._setData();
					p.control = DIV('quote-charts-panel');
					DIV('quote-charts-panel-wrapper').append(p.control).appendTo(p.controlParent);
					p._createContent();
				}
				
			},
			_setData: function() {
				vendors = p.data.vendorList || [];
				// 葛军 当前dashboard的module Id 给出不同的timePeriods 集合来显示， 如果1,2,3 为unQuote 否则 为 quote
				if(p.data.dashboardModuleId == 1 || p.data.dashboardModuleId == 2 || p.data.dashboardModuleId == 3) {
					timePeriods = p.data.timePeriodList || [];
				} else {
					timePeriods = p.data.timePeriodListQuote || [];
				}
				products = p.data.productList || [];
				productLabels = p.data.productLabelList || [];
				provinces = p.data.provinceList || [];
				auditReferences = p.data.auditReferenceList || [];
				budgets = p.data.budgetList || [];
				budgetOwners = p.data.budgetOwnerList || [];
				
				quoteProducts = p.data.quoteProductList || [];
				quoteProductLabels = p.data.quoteProductLabelList || [];
				quoteAgings = p.data.quoteAgingList || [];
				quoteIssuedStatus = p.data.quoteIssuedStatusList || [];
				quoteNetTypes = p.data.quoteNetTypeList || [];
				quoteVendors = p.data.quoteVendorList || [];
				quoteVendorResponseStatus = p.data.quoteVendorResposeStatusList || [];
				controlData = typeof(p.data.controlData) == 'string' ? $.parseJSON(p.data.controlData) : p.data.controlData || {};
			},
			_showError: this.showError,
			_createContent: function() {
				p._createRemoveButton();
				p._createDropdownFilterButton();
				p._createHeader();
				p._createModuleChart();
				p._createDropdownFilter();
				p._createOptions();
			},
			_createRemoveButton: function() {
				var self = this;
				p.removeBtn = $('<i class="delete-panel-icon icon"></i>').appendTo(p.control);
				p.removeBtn.click(function() {
					if(confirm('Are you sure you want to remove this dashboard?')) {
						$.ajax({
					        url: "removeUserDashboardViewMode.action",
					        type: "POST",
					        dataType: "text",
					        data: {"userDashboardModuleId": p.data.id},
					        success: function(o){
					        	p.removeAll();
					        	if (p.afterRemove) {
									p.afterRemove();
								}
					        },
							error : p._showError
					     });
					}
				});
			},
			_createDropdownFilterButton: function() {
				p.dropdownFilterBtn = $('<i class="slideDown-icon filters-icon"></i>').appendTo(p.control).click(function() {
					p.dropdownFilterDiv.slideDown(100);
					p.isDropdownFilterDivOpen = true;
				});
			},
			_createHeader: function() {
				
				p.headerDiv = DIV('quote-charts-panel-header clearfix').appendTo(p.control);
				p._createTitle();
//				p._createSubHeader();
			},
			_createTitle: function() {
				p.titleDiv = DIV('panel-title pull-left font-600').appendTo(p.headerDiv);
				p.titleContent = DIV('panel-title-wrapper clearfix').appendTo(p.titleDiv);
				p.titleInputContent = DIV('left-part pull-left').click(function(e) {
					p.titleInput.show();
					p.titleModifyContent.show();
					p.titleTextContent.hide();
					p.titleEditBtn.hide();
				}).appendTo(p.titleContent);
				p.titleTextContent = DIV('title-content pull-left').appendTo(p.titleInputContent);
				p.titleInput = $('<input type="text" class="edit-box">')
					.bind('keydown', function(event) {
						
						if( event.keyCode == 13) {
							p._saveTitle();
						}
					}).hide().appendTo(p.titleInputContent);
				p.titleEditBtn = $('<i class="icon edit-name-icon pull-right">').appendTo(p.titleInputContent);
				p.titleModifyContent = DIV('right-part pull-left').hide().appendTo(p.titleContent);
				
				p.titleConfirmBtn = $('<a href="javascript: void(0)" class="btn db-button-primary confirm-button themes-confirm">Confirm</a>').click(function(e) {
					p._saveTitle();
				}).appendTo(p.titleModifyContent);
				p.titleCancelBtn = $('<a href="javascript: void(0)" class="btn db-button-default cancel-button themes-cancel">Cancel</a>').click(function(e) {
					p.titleInput.val(p.data.name);
					p.titleInput.hide();
					p.titleModifyContent.hide();
					p.titleTextContent.show();
					p.titleEditBtn.show();
				}).appendTo(p.titleModifyContent);
				
				p.setTitle(p.data.name);
			},
			_saveTitle: function() {
				var value = p.titleInput.val(), message;
				if (value.length > 35) {
					message = "Dashboard module name can't be greater than 35 characters.";
				}
				if (value.length == 0) {
					message = "Dashboard module name cannot be empty.";
				}
				if (message) {
					alert(message);
					return;
				}
				$.ajax({
			        url: "updateUserDashboardModuleName.action",
			        type: "POST",
			        dataType: "json",
			        data: {"userDashboardModuleId": p.data.id,"userDashboardModuleName": value},
			        success: function(o){
			        	if (o.data == "sameName") {
			        		alert("The dashboard module name you entered already exists, please choose another one.");
			        	} else if (o.data == "success") {
			        		p.titleInput.hide();
							p.titleModifyContent.hide();
							p.titleTextContent.show();
							p.titleEditBtn.show();
							p.data.name = value;
							p.setTitle(p.data.name);
			        	}
			        },
					error : p._showError
			     });
			},	
			_createChartType: function() {
				var typeList = ['pie','column','list'],
					type,
					btn;
				p.chartTypeDiv = DIV('visual-switch-buttons pull-left').appendTo(p.optionsContentRightPart);
				for (var i = 0, length = typeList.length; i < length; i++) {
					type = typeList[i];
					btn = $('<i class="'+type+'-icon icon">').appendTo(p.chartTypeDiv)
						.attr('type', type)
						.toggleClass('selected',controlData.viewMode && controlData.viewMode == typeList[i])
						.click(function() {
							var viewMode = $(this).attr('type');
							
							if ($(this).hasClass('selected')) {
								
							} else {
								controlData.viewMode = viewMode;
								p._switchResultDiv(viewMode);
								$(this).siblings().removeClass('selected');
								$(this).addClass('selected');
								$.ajax({
							        url: "changeUserDashboardViewMode.action",
							        type: "POST",
							        dataType: "text",
							        data: {"userDashboardModuleId": p.data.id, "viewMode": viewMode},
							        success: function(o){},
									error : p._showError
							     });
							}
							
							
						});
				}
			},
			_createMoveSort: function() {
				
		        p.moveDiv = DIV('move-icons pull-left').appendTo(p.optionsContentRightPart);
				p.moveLeftBtn = $('<i class="backward-icon move-icon"></i>').click(function() {
					if (p.move) {
						p.move(-1);
					}
				}).appendTo($('<span class="backward">').appendTo(p.moveDiv));
				
				p.isShowLeftMoveBtn(p.showLeftMoveBtnFlag);
				
				p.moveRightBtn = $('<i class="forward-icon move-icon"></i>').click(function() {
					if (p.move) {
						p.move(1);
					}
				}).appendTo($('<span class="forward">').appendTo(p.moveDiv));
				
				p.isShowRightMoveBtn(p.showRightMoveBtnFlag);
			},
			_createSubHeader: function() {
				
				p.subHeaderDiv = DIV('filter-subpanel clearfix').appendTo(p.control);
				p._createCondition();
			},
			_createCondition: function() {
				var canUseControlList = p.data.canUseControlList, controlDiv,
					canUseControl, filterValue = '';
				
				p.conditionContent = DIV('condition panel-filters pull-left').appendTo(p.subHeaderDiv);
				p.conditionContent.empty();
				for (var i = 0, length = canUseControlList.length; i < length; i++) {
					canUseControl = canUseControlList[i];
					filterValue = p._getValueByControlkey(canUseControl.controlKey);
					if (filterValue.length) {
						controlDiv = DIV('panel-filter-item clearfix is-modul-filter').attr('is-show-filter', 'N').appendTo(p.conditionContent);
						DIV('filter-type').appendTo(controlDiv).append(canUseControl.controlName + ': ');
						var filterContent = DIV('filter-content').appendTo(controlDiv).attr('control-key', canUseControl.controlKey)
							.click(function(e) {
								var opts = p._getFilterOptionsByCanUseConditionKey($(this).attr('control-key')),
								target = $(e.target), offset = target.offset(), parent = $(this).parent(),
								position = {x: offset.left, y: offset.top};
									
								opts.callback = p._submit;
								opts.userDashboardModuleId = p.data.id;
								opts.pointerLayout = 'left';
								if (!saninco.dashboardFilter) {
									saninco.dashboardFilter = new saninco.DashboardModuleFilter({callback: p._submit});
								} 
								
								if (parent.attr('is-show-filter') == "N") {
									saninco.dashboardFilter.openFilter(position, opts);
									$(document).find('.is-modul-filter').attr('is-show-filter', 'N');
									parent.attr('is-show-filter','Y');
								} else {
									saninco.dashboardFilter.closeFilter();
									parent.attr('is-show-filter','N');
								}
								if (e.stopPropagation)
									e.stopPropagation();
								else
									e.cancelBubble = true;
							});
						if (filterValue.length > 50)
							filterValue = filterValue.substring(0, 50) + '...';
						if (canUseControl.controlKey == "timePeriod") { // 过滤条件为 Time Period.
							filterContent.addClass('time-period--type');
							filterContent.append(filterValue);
						} else {
							filterContent.append('<div class="filter-content-item-text" >'+filterValue+'</div>')
								.append($('<i class="delete-item-icon icon" control-key="' + canUseControl.controlKey + '"></i>').click(function(e) {
									p._submit({userDashboardModuleId: p.data.id, controlKey: $(this).attr('control-key'),controlValue: []});
									controlDiv.remove();
									if (e.stopPropagation)
										e.stopPropagation();
									else
										e.cancelBubble = true;
								}));
						}
					}
				}
				
			},
			_createOptions: function() {
				
				p.optionsPanel = DIV('filter-options-panel').hide().appendTo(p.control);
				p.optionsContent = DIV('filter-options-panel-content clearfix').appendTo(p.optionsPanel);
				
				p.optionsContentLeftPart = DIV('left-part pull-left'); // Options 中的左半部分。
				p.optionsContentRightPart = DIV('right-part pull-right'); // Options 中的右半部分。
				$('<i class="options-slideDown-icon options-icon"></i>').click(function(){
					p.optionsPanel.slideUp(100);
				}).appendTo(p.optionsPanel);
				
				p._createListBy();
				p._createIsShowPercent();

				p.optionsContentLeftPart.appendTo(p.optionsContent); // 添加到 options content 中。
				p._createChartType();
				p._createMoveSort();

				p.optionsContentRightPart.appendTo(p.optionsContent); // 添加到 options content 中。


				$('<i class="options-slideup-icon options-icon"></i>').click(function() {
					p.optionsPanel.slideDown(100);
				}).appendTo(p.control);
			},
			_createIsShowPercent: function() {
				DIV('percentage-checkbox-item pull-left')
					.attr('module-id',p.data.id)
					.click(function() {
						var checkBox = $(this).find('input[type="checkbox"]'),
							isShow = !checkBox.attr('checked');
						$.ajax({
					        url: "updateUserDashboardModuleShowPercent.action",
					        type: "POST",
					        dataType: "json",
					        data: {"userDashboardModuleId": p.data.id,"moduleShowPercentFlag": isShow ? 'Y' : 'N'},
					        success: function(o){
					        	if(isShow)
					        		checkBox.attr('checked', true);
								else
									checkBox.removeAttr('checked');
								p._setIsShowValuePercent(isShow);
					        },
							error : p._showError
					     });
						
						
					})
					.append($('<input id="is-show-percent-'+p.data.id+'" type="checkbox"'+(p.isShowValuePercent ? 'checked' : '')+'/>'))
					.append('<i class="checkbox-button"></i>')
					.append('<span class="font-12 show-percentage-text">Show Percentage</span>')
					.append('<i class="show-percentage-icon"></i>')
					.appendTo(p.optionsContentLeftPart);
			},
			_createListBy: function() {
				var	titleContent, titleContentTitle, listContent, listContentList,
					controlListBy = controlData.listBy, isHasList = false,
					listBys, canUseControlList = p.data.canUseControlList;
				p.listByContent = DIV('list-by-vendor-dropdown-container');
				DIV('dropdowns-container pull-left').appendTo(p.optionsContentLeftPart).append(p.listByContent);
				titleContent = DIV('list-by-vendor-dropdown-title').appendTo(p.listByContent).click(function(e) {
					titleContentTitle.toggleClass('foldup');
					titleContentTitle.toggleClass('border-bottom-none');
					if (isHasList) {
						listContent.toggle();
					}
					if (e.stopPropagation)
						e.stopPropagation();
					else
						e.cancelBubble = true;
				});
				titleContentTitle = DIV('select-title foldup pointer font-14 select-none').appendTo(titleContent);
				listContent = DIV('list-vendor-container').appendTo(p.listByContent).hide();
				listContentList = $('<ul class="list-by-list" />').appendTo(listContent);
				for (var i = 0; i < canUseControlList.length; i++) {
					if (listBys = controlListBy[canUseControlList[i].controlKey]) {
						for (var j = 0; j < listBys.length; j++) {
							if (listBys[j].isSelected == 'Y') {
								titleContentTitle.append(listBys[j].name);
							} else {
								listContentList.append($('<li class="list-by-list-item" control-key="'+ canUseControlList[i].controlKey +'" key="'+ listBys[j].key +'">'+listBys[j].name+'</li>')
										.click(function(e) {
											var controlKey = $(this).attr('control-key'),
												controlFilterKey = $(this).attr('key');
											listContent.hide();
											titleContentTitle.toggleClass('border-bottom-none');
											$.ajax({
										        url: "changeUserDashboardListby.action",
										        type: "POST",
										        dataType: "text",
										        data: {controlKey: controlKey, controlFilterKey: controlFilterKey, userDashboardModuleId: p.data.id},
										        success: function(o){
										        	result = $.parseJSON(o);
										        	p.clearn();
													p.data = $.extend(p.data, result.data);
													p._setData();
													p._createContent();
													p.optionsPanel.slideDown(0);
													if (p.afterRefresh) {
														p.afterRefresh();
													}
										        	
										        },
												error : p._showError
										     });
											if (e.stopPropagation)
												e.stopPropagation();
											else
												e.cancelBubble = true;
										}));
								isHasList = true;
							}
						}
					}
				}
			},
			_switchResultDiv: function(viewMode) {
				var isList = viewMode == 'list',
					isPie = viewMode == 'pie',
					isColumn = viewMode == 'column';
				if (p.loadingDiv.is(":visible"))
					return;
				
				p.resultDiv.toggleClass('visual-container', isList)
					.toggleClass('visual-container-chart', !isList);

				if (isList) {
					p.listDiv.show();
					p.chartDiv.hide();
	        	} else {
	        		p.listDiv.hide();
					p.chartDiv.show();
					p.chartDiv.toggleClass('pie-chart-wrapper', isPie)
						.toggleClass('bar-chart-wrapper', isColumn);
					if (p.chart) {
						p.chart.series[0].update({
				            type: viewMode,
				            dataLabels: {
				                enabled: isPie
				            }
				        });
					}
	        	}
			},
			_setIsShowValuePercent: function(isShow) {
				p.isShowValuePercent = isShow;
				if (p.chart) {
					p.chart.series[0].update({
						dataLabels: {
			                format: '<span style="font-size:9px">{point.name}</span>' +
			                '<br/><span style="font-size:9px;font-weight:normal;">{point.other}'+ (p.isShowValuePercent ? '/{point.y:.2f}%' : '') +'</span>'
			            }
					});
					p.chart.update({
						tooltip : {
							pointFormat : '<span>{point.name}</span> <br/> {point.other} '+ (p.isShowValuePercent ? '/ <b>{point.y:.2f}%</b> of total<br/>': '')
						},
					});
				}
				if (p.listDiv) {
					if (isShow)
						p.listDiv.find('.value-percent').show();
					else
						p.listDiv.find('.value-percent').hide();
				}
				
			},
			_createModuleChart: function() {
				p.resultDiv = DIV('dashboard-result').appendTo(p.control);
				p.loadingDiv = DIV('dashboard-result-loding').append('Loading, please wait...').appendTo(p.resultDiv);
				p.chartDiv = DIV('dashboard-chart').hide().attr('id', 'dashboard-chart-' + p.data.id).appendTo(p.resultDiv);
				p.listDiv = DIV('list-visual-wrapper').hide().appendTo(p.resultDiv);
				p.loadingDiv.show();
				$.ajax({
			        url: "getChartDataByUserModuleId.action",
			        type: "POST",
			        dataType: "text",
			        data: {"userDashboardModuleId": p.data.id},
			        success: function(o){
			        	var viewMode = controlData.viewMode,
			        		result = $.parseJSON(o);
			        	Highcharts.setOptions({
			                lang: {
			                    noData: 'No Result'
			                },
			                colors: ['#808284','#BBE4EC','#BBBDC0']
			            });
			        	p.chart = new Highcharts.chart('dashboard-chart-'+p.data.id,
								{
									chart : {
										type : viewMode != 'list' ? viewMode : 'pie'
									},
									credits: {enabled: false},
									title : {text:null},
									subtitle : {text:null},
									legend: {enabled: false},
									xAxis: {
								        type: 'category',
								        lineWidth: 0
								    },
								    yAxis: {
								    	title:'',
							            labels: {
							            	formatter: function() {
							            		if (this.value > 0)
							            			return this.value + ' %';
							            	}
							            }
								    },
									plotOptions : {
										pie:{
											size: (window.innerWidth < 487) ? (window.innerWidth < 376 ? 80 : 120) : 310 // 自适应扇形大小。
										},
										series : {
											dataLabels : {
												distance: (window.innerWidth < 487) ? 5 : 20, // 自适应扇形label长度。
												style: {
													'fontSize':'9px',
													'width':'100px',
													'white-space':'pre-wrap'
												},
//												crop:false,
												enabled: true,
												format: '<span style="font-size:9px">{point.name}</span>' +
													'<br/><span style="font-size:9px;font-weight:normal;">{point.other}'+ (p.isShowValuePercent ? '/{point.y:.2f}%' : '') +'</span>'
											}
										}
									},
									tooltip : {
										headerFormat : '',
										pointFormat : '<span>{point.name}</span> <br/> {point.other} '+ (p.isShowValuePercent ? '/ <b>{point.y:.2f}%</b> of total<br/>': '')
									},
									series : [{
										name : null,
										colorByPoint : true,
										data : result.data
										}],
									noData: {
//							            style: {
//							                fontWeight: 'bold',
//							                fontSize: '15px',
//							                color: '#303030'
//							            }
							        }
									});
			        	p._createResultList(result.data);
			        	p.loadingDiv.hide();
			        	p._switchResultDiv(viewMode);
			        	p._setIsShowValuePercent(p.isShowValuePercent);
			        },
					error : p._showError
			     });
			},
			_createResultList: function(data) {
				var table, item;
				p.listDiv.empty();
				p.listDiv.toggleClass('result-list-no-data', data.length == 0);
				if (data.length == 0) {
					p.listDiv.append('No Result');
				} else {
					table = $('<table class="vendor-table">').appendTo(p.listDiv);
					table.append('<tr><th class="list-by">List by</th><th class="result">Result</th><th class="percentage value-percent">Percentage</th></tr>');
					for (var i = 0, length = data.length; i < length; i++) {
						item = data[i];
						table.append('<tr><td class="vendor-name">'+item.name+'</td><td>'+item.other+'</td><td class="value-percent">'+item.y+'%</td></tr>');
					}
				}
				
			},
			_createDropdownFilter: function() {
				var canUseControlList = p.data.canUseControlList,
					canUseControl;
				p.dropdownFilterDiv = DIV('select-filter-panel-wrapper').appendTo(p.control);
				p.dropdownFilterPanel = DIV('select-filter-panel').appendTo(p.dropdownFilterDiv);
				p.dropdownFilterTitle = DIV('filter-panel-title clearfix').appendTo(p.dropdownFilterPanel);
				$('<span class="pull-left">Select the filter</span>').appendTo(p.dropdownFilterTitle);
				p.dropdownFilterContent = DIV('panel-content').appendTo(p.dropdownFilterPanel);
				$('<i class="slideUp-icon filters-icon"></i>').appendTo(p.dropdownFilterPanel).click(function() {
					p.dropdownFilterDiv.slideUp(100);
					p.isDropdownFilterDivOpen = false;
				});
				for (var i = 0, length = canUseControlList.length; i < length; i++) {
					canUseControl = canUseControlList[i];
					filterValue = p._getValueByControlkey(canUseControl.controlKey);
					if (filterValue.length > 50)
						filterValue = filterValue.substring(0,50) + '...';
					DIV('panel-content-item is-modul-filter')
						.attr('control-key', canUseControl.controlKey)
						.attr('is-show-filter', 'N')
						.click(function(e) {
								
							var opts = p._getFilterOptionsByCanUseConditionKey($(this).attr('control-key')),
							target = $(this).find('.filter-target'), offset = target.offset(),
							position = {x: offset.left, y: offset.top};
							
							opts.callback = p._submit;
							if (!saninco.dashboardFilter) {
								saninco.dashboardFilter = new saninco.DashboardModuleFilter();
							} 
							
							if ($(this).attr('is-show-filter') == "N") {
								saninco.dashboardFilter.openFilter(position, opts);
								$(document).find('.is-modul-filter').attr('is-show-filter', 'N');
								$(this).attr('is-show-filter','Y');
							} else {
								saninco.dashboardFilter.closeFilter();
								$(this).attr('is-show-filter','N');
							}
							
							
							if (e.stopPropagation)
								e.stopPropagation();
							else
								e.cancelBubble = true;
						})
						.append(
						DIV('col-md-3 col-sm-3 col-xs-3').append(
							DIV('filter-item-name').append(canUseControl.controlName)
						)
					).append(
						DIV('col-md-8 col-sm-8 col-xs-8').append(
							DIV('filter-item-content').append(filterValue)
							)	
					).append(
						DIV('col-md-1 col-sm-1 col-xs-1').append(
							$(' <i class="filter-target edit-icon icon">')
						)	
					).appendTo(p.dropdownFilterContent);
				}
				if (p.isDropdownFilterDivOpen) {
					p.dropdownFilterDiv.show();
				}
			},
			_submit: function(data) {
				
				data.userDashboardModuleId = p.data.id;
				$.ajax({
			        url: "saveControlData.action",
			        type: "POST",
			        dataType: "text",
			        data: $.param(data, true),
			        success: function(o){
			        	result = $.parseJSON(o);
			        	p.clearn();
						p.data = $.extend(p.data, result.data);
						p._setData();
						p._createContent();
						if (p.data.afterRefresh) {
							p.data.afterRefresh();
						}
			        	
			        },
					error : p._showError
			     });
			},
			_getFilterOptionsByCanUseConditionKey: function(key) {
				var options = {isShowConfirm: true, data: []},
					canUseConditions = [], conditionData = [];
				switch (key) {
					case "timePeriod" :
						options.isShowConfirm = false;
						canUseConditions.push({key: 'timePeriod', condition: timePeriods, controlData: controlData.timePeriod});
						break;
					case "vendor" :
						canUseConditions.push({key: 'vendor', isMultiple: true, condition: vendors, controlData: controlData.vendor});
						break;
					case "product" :
						canUseConditions.push({key: 'productLabel', title: 'Product Label', condition: productLabels, controlData: controlData.productLabel});
						canUseConditions.push({key: 'product', title:'Product', isMultiple: true, condition: products, controlData: controlData.product});
						break;
					case "region" :
						canUseConditions.push({key: 'region', isMultiple: true, condition: provinces, controlData: controlData.region});
						break;
					case "budget" :
						canUseConditions.push({key: 'budgetOwner', title: 'Budget Owner', condition: budgetOwners, controlData: controlData.budgetOwner});
						canUseConditions.push({key: 'budget', title:'Budget', isMultiple: true, condition: budgets, controlData: controlData.budget});
						break;
					case "cto" :
						canUseConditions.push({key: 'cto', isMultiple: true, condition: auditReferences, controlData: controlData.cto});
						break;
					case "quoteProduct" :
						canUseConditions.push({key: 'quoteProductLabel', title: 'Product Label', condition: quoteProductLabels, controlData: controlData.quoteProductLabel});
						canUseConditions.push({key: 'quoteProduct', title:'Product', isMultiple: true, condition: quoteProducts, controlData: controlData.quoteProduct});
						break;
					case "quoteVendor" :
						canUseConditions.push({key: 'quoteVendor', isMultiple: true, condition: quoteVendors, controlData: controlData.quoteVendor});
						break;
					case "quoteAging" :
						canUseConditions.push({key: 'quoteAging', isMultiple: true, condition: quoteAgings, controlData: controlData.quoteAging});
						break;
					case "quoteIssuedStatus" :
						canUseConditions.push({key: 'quoteIssuedStatus', isMultiple: true, condition: quoteIssuedStatus, controlData: controlData.quoteIssuedStatus});
						break;
					case "quoteNetType" :
						canUseConditions.push({key: 'quoteNetType', isMultiple: true, condition: quoteNetTypes, controlData: controlData.quoteNetType});
						break;
					case "quoteVendorResponseStatus" :
						canUseConditions.push({key: 'quoteVendorResponseStatus', isMultiple: true, condition: quoteVendorResponseStatus, controlData: controlData.quoteVendorResponseStatus});
						break;
				}
				for (var i = 0, length = canUseConditions.length; i < length; i++) {
					var conditionData = {}, canUseCondition = canUseConditions[i], conditions = [];
					conditionData.key = canUseCondition.key;
					if (canUseCondition.isMultiple)
						conditionData.isMultiple = canUseCondition.isMultiple;
					if (canUseCondition.title)
						conditionData.title = canUseCondition.title;
					for (var j = 0, jLength = canUseCondition.condition.length; j < jLength; j++) {
						var key = (canUseCondition.condition[j] && canUseCondition.condition[j].id != undefined ? canUseCondition.condition[j].id : canUseCondition.condition[j]),
							name = (canUseCondition.condition[j] && canUseCondition.condition[j].labelName ? canUseCondition.condition[j].labelName : canUseCondition.condition[j]);
						var condition = {key: key, name: name};
						if (canUseCondition.controlData) {
							for (var k = 0, kLength = canUseCondition.controlData.length; k < kLength; k++) {
								if (key == canUseCondition.controlData[k]) {
									condition.isSelected = true;
								}
							}
						}
						conditions.push(condition);
					}
					conditionData.condition = conditions;
					options.data.push(conditionData);
				}
					
				return options;
			},
			_getValueByControlkey: function(key) {
				var result = [];
				switch (key) {
					case "timePeriod" :
						result = p._getValuesBykey(controlData.timePeriod, "id", timePeriods);
						break;
					case "vendor" :
						result = p._getValuesBykey(controlData.vendor, "id", vendors);
						break;
					case "product" :
						result = p._getValuesBykey(controlData.product, "id", products);
						if (result.length == 0) {
							result = p._getValuesBykey(controlData.productLabel, null, productLabels);
						}
						break;
					case "region" :
						result = p._getValuesBykey(controlData.region, "id", provinces);
						break;
					case "budget" :
						result = p._getValuesBykey(controlData.budget, "id", budgets);
						if (result.length == 0) {
							result = p._getValuesBykey(controlData.budgetOwner, null, budgetOwners);
						}
						
						break;
					case "cto" :
						result = p._getValuesBykey(controlData.cto, "id", auditReferences);
						break;
					case "quoteProduct" :
						result = p._getValuesBykey(controlData.quoteProduct, "id", quoteProducts);
						if (result.length == 0) {
							result = p._getValuesBykey(controlData.quoteProductLabel, null, quoteProductLabels);
						}
						break;
					case "quoteVendor" :
						result = p._getValuesBykey(controlData.quoteVendor, "id", quoteVendors);
						break;
					case "quoteAging" :
						result = p._getValuesBykey(controlData.quoteAging, "id", quoteAgings);
						break;
					case "quoteIssuedStatus" :
						result = p._getValuesBykey(controlData.quoteIssuedStatus, "id", quoteIssuedStatus);
						break;
					case "quoteNetType" :
						result = p._getValuesBykey(controlData.quoteNetType, "id", quoteNetTypes);
						break;
					case "quoteVendorResponseStatus" :
						result = p._getValuesBykey(controlData.quoteVendorResponseStatus, "id", quoteVendorResponseStatus);
						break;
				}
				return result.length ? result.join(',') : '';
			},
			_getValuesBykey: function(conditionList , keyName, dataList) {
				var data, result = [], conditionData;
				if (!conditionList || !dataList) {
					return '';
				}
				for (var i = 0, length = conditionList.length; i < length; i++) {
					conditionData = conditionList[i];
					for (var j = 0, jLength = dataList.length; j < jLength; j++) {
						data = dataList[j];
						if ((keyName ? data[keyName] : data) == conditionData) {
							result.push(data['labelName'] ? data['labelName'] : data);
						}
					}
				}
				
				return result;
			},
			showDropdownFilter: function() {
				p.dropdownFilterDiv.show();
				p.isDropdownFilterDivOpen = true;
			},
			setTitle: function(title) {
				p.titleTextContent.html(title);
				p.titleTextContent.attr('title', title);
				p.titleInput.val(title);
			},
			removeAll: function() {
				p.controlParent.remove();
			},
			clearn: function() {
				p.control.empty();
			},
			setAfterRemove: function(afterRemove) {
				p.afterRemove = afterRemove;
			},
			setMove: function(move) {
				p.move = move;
			},
			isShowLeftMoveBtn: function(isShow) {
				p.showLeftMoveBtnFlag = isShow;
				if (isShow) {
					p.moveLeftBtn.show();
				} else {
					p.moveLeftBtn.hide();
				}
			},
			isShowRightMoveBtn: function(isShow) {
				p.showRightMoveBtnFlag = isShow;
				if (isShow) {
					p.moveRightBtn.show();
				} else {
					p.moveRightBtn.hide();
				}
			}
		}
		this.submit = p._submit;
		this.removeAll = p.removeAll;
		this.setTitle = p.setTitle;
		this.control = p.control;
		this.showDropdownFilter = p.showDropdownFilter;
		this.setAfterRemove = p.setAfterRemove;
		this.setMove = p.setMove;
		this.isShowLeftMoveBtn = p.isShowLeftMoveBtn;
		this.isShowRightMoveBtn = p.isShowRightMoveBtn;
		return p.init.apply(p, arguments);
	}
	saninco.DashboardModuleFilter = function(options) {
		opts = $.extend({
			element: "dashboard-module",
			isShowConfirm: true,
			data:{}
		}, options);
		var DIV = function(style) {
			return $('<div>').addClass(style);
		}
		var p = {
			control: {},
			init: function() {
				p.callback = opts.callback;
				p.control = DIV('dashboard-filter-list border-box').hide().appendTo($(document.body));
				p._createContent();
				p._bindHideEvent();
			},
			_createContent: function() {
				p._createSeletedItemsContent();
				p._createSearchContent();
				p._createConditionContent();
			},
			_createSeletedItemsContent: function() {
				p.selectedItemsDiv = DIV('selected-result clearfix').appendTo(p.control)
					.append($('<i class="clear-icon icon"></i>').click(function() {
			            p.condtionContent.find('input').removeAttr('checked');
						p._updateData(null, null, null);
					}));
				p.selectedItemsContent = DIV('').appendTo(p.selectedItemsDiv);
			},
			_createSeletedItems: function() {
				var itemData, condition, isHasSelected = false;
				p.selectedItemsContent.empty();
				for (var j = 0; j < p.data.length; j++) {
					condition = p.data[j].condition;
					for (var i = 0, length = condition.length; i < length; i++) {
						itemData = condition[i];
						if (itemData.isSelected) {
							isHasSelected = true;
							DIV('selected-result-item')
							.append($('<span class="selected-result-content" >'+itemData.name+'</span>'))
							.append($('<i class="delete-icon icon" control-key="'+p.data[j].key+'" filter-key="'+itemData.key+'">').click(function() {
								var controlKey = $(this).attr('control-key'),
									filterKey = $(this).attr('filter-key');
								p._updateData(controlKey, filterKey, false);
								p.condtionContent.find('.filter-control-checkbox-item[control-key="'+controlKey+'"][filter-key="'+filterKey+'"]').find('input').removeAttr('checked');
								p.condtionContent.find('.filter-control-radio-item[control-key="'+controlKey+'"][filter-key="'+filterKey+'"]').find('input').removeAttr('checked');
							}))
							.appendTo(p.selectedItemsContent);
						}
					}
				}
				if (!isHasSelected) {
					p.selectedItemsDiv.hide();
				} else {
					p.selectedItemsDiv.show();
				}
			},
			_createSearchContent: function() {
				
				p.searchContent = DIV('search-dashboard-filter clearfix').appendTo(p.control);
				p.searchFilterDiv = DIV('search-input-wrapper pull-left').appendTo(p.searchContent);
				p.searchInput = $('<input class="search-dashboard-filter-input" type="text">').keyup(function() {
					var value = $(this).val();
					p.condtionContent.find('.is-filter-value').each(function() {
						var name = $(this).attr('is-filter-value');
						if (name.toLowerCase().search(value.toLowerCase()) >= 0 || value.length == 0) {
							$(this).show();
						} else {
							$(this).hide();
						}
					});
				}).appendTo(p.searchFilterDiv);
				p.buttonsDiv = DIV('search-buttons pull-left').appendTo(p.searchContent);
				p.confirmBtn = $('<button class="btn db-button-primary narrow-button search-confirm" >').append('Confirm').click(function() {
					p._submit();
				}).appendTo(p.buttonsDiv);
				p.cancelBtn = $('<button class="btn db-button-default narrow-button search-cancel" >').append('Cancel').click(function() {
					p.closeFilter();
				}).appendTo(p.buttonsDiv);
				
			},
			_createConditionContent: function() {
				p.condtionContent = DIV('dashboard-filter-content-list').appendTo(p.control);
			},
			_createConditionContentCondition: function() {
				var condition, itemData, filterDiv, data, conditionDiv, conditionListDiv;
				p.condtionContent.empty();
				for (var j = 0, jLength = p.data.length; j < jLength; j++) {
					data = p.data[j];
					
					// Dashboard Filter List 中，如果包含两个部分，将第二部分
					// 应用 padding 样式， 添加 CSS 类 .radio-controls.
					filterDiv = DIV('filter-control-key pull-left').appendTo(p.condtionContent).attr('control-key', data.key);
					if ( j > 0 ) {
						filterDiv.addClass('radio-controls');
					} 
					
					if (p.data.length) {
						filterDiv.css({width: 100/p.data.length + '%'});
					}
					if (data.title) {
						DIV('dashboard-filter-name-title font-12').append(data.title).appendTo(filterDiv);
					}
					conditionDiv = DIV('').appendTo(filterDiv);
					conditionDiv
						.toggleClass('checkbox-container', data.isMultiple)
						.toggleClass('dashboard-filter-radio-container', !data.isMultiple);
					condition = data.condition;
					
					for (var i = 0, length = condition.length; i < length; i++) {
						itemData = condition[i];
						if (data.isMultiple) {
							DIV('checkbox-item filter-control-checkbox-item is-filter-value')
							.attr('control-key', data.key)
							.attr('filter-key', itemData.key)
							.attr('is-filter-value', itemData.name)
							.append('<input type="checkbox" '+(itemData.isSelected ? 'checked' : '')+' ><i class="checkbox-button icon"></i>   <label class="font-12">' + itemData.name + '</label>')
							.click(function() {
								var isSelected = !$(this).find('input[type="checkbox"]').is(':checked'),
									controlKey = $(this).attr('control-key'), 
									filterKey = $(this).attr('filter-key');
								if (isSelected) {

						            $(this).find('input[type="checkbox"]').attr('checked', true);
						        } else {

						            $(this).find('input[type="checkbox"]').removeAttr('checked');
						        }
								p.condtionContent.find('.filter-control-key[control-key!="'+controlKey+'"]').find('input').removeAttr('checked');
								p._updateData(controlKey, filterKey, isSelected);
							}).appendTo(conditionDiv);
						} else {
							DIV('radio-item filter-control-radio-item is-filter-value')
							.attr('control-key', data.key)
							.attr('filter-key', itemData.key)
							.attr('is-filter-value', itemData.name)
							.append('<input type="radio" name="global-controller" '+(itemData.isSelected ? 'checked' : '')+' ><i class="radio-button icon"></i>   <label>' + itemData.name + '</label>')
							.click(function() {
								var isSelected = !$(this).find('input[type="radio"]').is(':checked'),
									controlKey = $(this).attr('control-key'), 
									filterKey = $(this).attr('filter-key');
								if (isSelected) {
							        $(this).find('input[type="radio"]').attr('checked', true);
							        p._updateData(controlKey, filterKey, isSelected);
							    }
							    $(this).siblings().find('input[type="radio"]').removeAttr('checked');
							    p.condtionContent.find('.filter-control-key[control-key!="'+controlKey+'"]').find('input').removeAttr('checked');
							    if (!p.isShowConfirm) {
							    	p._submit();
							    }
							}).appendTo(conditionDiv);
						}

					}
				}
			},
			_submit: function() {
				var callback = p.callback;
				p.closeFilter();
				if (callback)
	        		callback(p._getSubmitData());
			},
			_updateData: function(controlKey, filterKey, isSelected) {
				var condition, data;
				for (var i = 0, length = p.data.length; i < length; i++) {
					data = p.data[i];
					if (data.key == controlKey) {
						for (var j = 0, jLength = data.condition.length; j < jLength; j++) {
							condition = data.condition[j];
							if (data.isMultiple) {
								if (condition.key == filterKey) 
									condition.isSelected = isSelected;
							} else {
								if (condition.key == filterKey) {
									condition.isSelected = isSelected;
								} else {
									condition.isSelected = false;
								}
							}
						}
						
					} else {
						for (var j = 0, jLength = data.condition.length; j < jLength; j++) {
							condition = data.condition[j];
							condition.isSelected = false;
						}
					}
				}
				if (p.isShowConfirm)
					p._createSeletedItems();
			},
			_getSubmitData: function() {
				var condition, data, submitData = {}, controlValue = []; 
				for (var i = 0, length = p.data.length; i < length; i++) {
					data = p.data[i];
					controlValue = [];
					if (!submitData.controlKey)
						submitData.controlKey = data.key;
					for (var j = 0, jLength = data.condition.length; j < jLength; j++) {
						condition = data.condition[j];
						if (condition.isSelected) {
							controlValue.push(condition.key);
						}
					}
					if (controlValue.length) {
						submitData.controlKey = data.key;
						submitData.controlValue = controlValue;
					}
				}
				submitData.userDashboardModuleId= p.userDashboardModuleId;
				return submitData;
			},
			_bindHideEvent: function() {
				
				p.control.bind('click', function(e) {
					if (e.stopPropagation)
						e.stopPropagation();
					else
						e.cancelBubble = true;
				});
				
				$(document).bind('click', function() {
					if (!p.control.is(':hidden')) {
						p.closeFilter();
					}
				});
				
				p.control.show();
			},
			_setPosition: function(position) {
				p.control.toggleClass('pointer-left', p.pointerLayout == 'left');
				if (p.pointerLayout == 'left') {
					x = position.x;
					y = position.y + 27;
					p.control.css({
						left: x,
						top: y,
						right: ''
					});
				} else {
					
					x = $(document).width() - position.x - 52;
					y = position.y + 27;
					p.control.css({
						left: '',
						right: x,
						top: y
					});
				}
				
			},
			setPosition: function(x, y) {
				p.control.css({
					left: x,
					top: y
				});
			},
			closeFilter: function() {
				p.control.hide();
				$(document).find('.is-modul-filter').attr('is-show-filter', 'N');
			},
			isShow: function() {
				return !p.control.is(':hidden');
			},
			openFilter: function(position, options) {
				p.searchInput.val('');
				p.callback = options.callback;
				p.pointerLayout = options.pointerLayout || 'right';
				p.data = options.data;
				p.isShowConfirm = options.isShowConfirm;
				p.userDashboardModuleId = options.userDashboardModuleId;
				if (p.isShowConfirm) {
					p.selectedItemsDiv.show();
					p.searchContent.show();
					p._createSeletedItems();
				} else {
					p.selectedItemsDiv.hide();
					p.searchContent.hide();
				}
				p._createConditionContentCondition();
				p._setPosition(position);
				p.control.show();
			}
			
		}
		this.openFilter = p.openFilter;
		this.closeFilter = p.closeFilter;
		this.setPosition = p.setPosition;
		this.isShow = p.isShow;
		return p.init.apply(p, arguments);
	}
})();
