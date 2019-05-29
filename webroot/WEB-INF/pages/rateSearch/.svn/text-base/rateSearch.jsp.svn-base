<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Contract Tab</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="之前将 Contract, Tariff, MtM 三种验证规则分为三个Tab来管理，
    现在需要将这三个Tab组合成一个Tab， 名字叫做Rate Search。之后对于 contract 还有一个特殊的 summary list view.">

    <!-- simpleCalendar.css -->
    <link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
    <link rel="stylesheet" type="text/css" href="include/css/hint.min.css">
    
    <!-- 前台过滤条件中下拉列表的样式 -->
    <link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css">
    <link rel="stylesheet" type="text/css" href="include/css/rateSearch/rateSearch.css">

    <!-- SimpleCalendar 日期插件-->
    <script type="text/javascript" src="include/javascript/Calendar/simplecalendar.js"></script>
    <script type="text/javascript" src="include/javascript/yui2/tabview-min.js"></script>
    <script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
    <script type="text/javascript" src="include/javascript/jquery/jquery.ui.core.js"></script>
    <script type="text/javascript" src="include/javascript/jquery/jquery.ui.widget.js"></script>
    <script type="text/javascript" src="include/javascript/jquery/jquery.ui.accordion.js"></script>
    <script type="text/javascript" src="include/javascript/ccm/common/common.js"></script>
    <script type="text/javascript" src="include/javascript/ccm/common/simpleValidation.js"></script>
    <script type="text/javascript" src="include/javascript/ccm/quicklink.js"></script>
    <script type="text/javascript" src="include/javascript/rateSearch/rateSearch.js"></script>
    <script>

        // Summary Vendor Name Filter.
        var summary_vendor_name_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${summaryVendorNameList}" var="summaryVendorNameListMap">
                ,{
                    "id": "${summaryVendorNameListMap.key}",
                    "name": "${summaryVendorNameListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(summaryVendorNameList)}
        };
    
        // Vendor Name Filter.
        var vendor_name_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${vendorNameList}" var="vendorNameListMap">
                ,{
                    "id": "${vendorNameListMap.key}",
                    "name": "${vendorNameListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(vendorNameList)}
        };



        // Contract # /Tariff Reference Filter.
        var tariff_name_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${contractOrTariffNameList}" var="contractOrTariffNameListMap">
                ,{
                    "id": "${contractOrTariffNameListMap.key}",
                    "name": "${contractOrTariffNameListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(contractOrTariffNameList)}
        };


        
        // Sub Product Filter.
        var sub_product_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${subProductList}" var="subProductListMap">
                ,{
                    "id": "${subProductListMap.key}",
                    "name": "${subProductListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(subProductList)}
        };

        

        /*通过 reference type 过滤出  rule reference*/
        function filterRulesReference(e) {

        	showLoadingModalLayer();
            
            $.ajax({
                type: 'GET',
                url: 'filterRulesReference.action?searchRateSearchVO.referenceType=' + e.target.value,
                dataType: 'text',
                success: function(data) {
                    
                    var referenceNames = JSON.parse( data );
                    var referenceNamesObject = {};
                    
                    if ( Array.isArray(referenceNames) ) {

                    	referenceNamesObject.total = referenceNames.length;
                    	referenceNamesObject.results = [];
                    	referenceNamesObject.results.push({id: 'all', name: ''});

                    	// 遍历添加 items.
                    	$.each(referenceNames, function(i, v) {
                        	
                    		referenceNamesObject.results.push({
                    		    id: v.key,
                    		    name: v.value
                        	});
                        });
                    }
                    // 阻止数据结构
	            	
	                // 销毁组件。
	            	$('#contractOrTariffNameList').html('');
	            	// 渲染 flexbox dropdown list.
	            	form0_contractOrTariffName_dropdownList = $('#contractOrTariffNameList').flexbox(referenceNamesObject, {
	                    maxCacheBytes : 200000,
	                    highlightMatches : true,
	                    autoCompleteFirstMatch : false,
	                    paging: false,
	                    width : 240,
	                    onSelect: function(){
	                    }
	                });
	                form0_contractOrTariffName_dropdownList.setValue("all");

	                hideLoadingModalLayer();
                },

                error: function(e) {
                    console.error('Request is failed!');
                }
            });
        }

   
        $(function() {

            // Summary Vendor Name.
            form0_summaryVendorName_dropdownList = $('#summaryVendorNameFilterList').flexbox(summary_vendor_name_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_summaryVendorName_dropdownList.setValue("all");

            // Vendor Name.
            form0_vendorName_dropdownList = $('#vendorNameFilterList').flexbox(vendor_name_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 280,
                onSelect: function(){
                }
            });
            form0_vendorName_dropdownList.setValue("all");
            
            // Contract # /Tariff Reference
            form0_contractOrTariffName_dropdownList = $('#contractOrTariffNameList').flexbox(tariff_name_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 240,
                onSelect: function(){
                }
            });
            form0_contractOrTariffName_dropdownList.setValue("all");

            // Sub Product
            form0_subProduct_dropdownList = $('#subProductFilterList').flexbox(sub_product_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_subProduct_dropdownList.setValue("all");
        })
    </script>

</head>
<body>
    <div id="pageContainer" class="tabForm">
        <h3 class="page-title">Rate Search</h3>

        <div class="tariff-rules-content">
            <div class="search-module">
                <s:form id="form0" action="" onsubmit="">
                    <table class="tabDetailView">
                        <tbody>
                            <tr>
                                <td class="tabDetailViewDL left-part" width="52%">
                                    <div class="searchItemPanel">
                                        <table class="main-filter-conditions">

                                            <tr>
                                                <td>Rate View</td>
                                                <td>
                                                    <select id="rate-view-type-list" style="width: 230px;">
                                                        <option value="1" selected>Details Search</option>
                                                        <option value="2">Contract Summary</option>
                                                     </select>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td width="30%">Summary Vendor Name</td><!-- 模糊查询 -->
                                                <td class="summary-vendor-name-list">
                                                    <div id="summaryVendorNameFilterList"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Vendor Name</td><!-- 模糊查询 -->
                                                <td class="vendor-name-list">
                                                    <div id="vendorNameFilterList"></div>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Stripped Circuit Number</td>
                                                <td>
                                                    <s:textfield cssClass="stripped-circuit-number validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Sub Product</td>
                                                <td class="sub-product-list">
                                                    <div id="subProductFilterList"></div>
                                                </td>
                                            </tr>
                                               
                                        </table>
                                    </div>
                                </td>
                                
                                <td class="tabDetailViewDL">
                                    <div class="searchItemPanel">
                                         <table class="">
                                             <tbody>

                                                <tr>
                                                    <td>Contract / Tariff / MtM</td>
                                                    <td>
                                                        <select id="reference-type-list" style="width: 230px;" onchange="filterRulesReference(event)">
                                                            <option value=""></option>
                                                            <option value="Contract">Contract</option>
                                                            <option value="Tariff">Tariff</option>
                                                            <option value="MtM">MtM</option>
                                                         </select>
                                                    </td>
                                                </tr>
                                                
                                                <tr>
                                                    <td width="30%">Contract # /Tariff Reference</td>
                                                    <td>
                                                        <div id="contractOrTariffNameList"></div>
                                                    </td>
                                                </tr>
                                                 
                                                 <tr id="first-custom-filters">
                                                     <td>
                                                        <span>CRTC</span>
                                                        <input type="text" 
                                                            class="crtc-number validate-alp" 
                                                            autocomplete="off"/>
                                                      </td>
                                                      
                                                      <td>
                                                        <span>Part/Section</span>
                                                         <input type="text" 
                                                            class="part-or-section validate-alp" 
                                                            autocomplete="off"/>
                                                      </td>
    
                                                 </tr>
                                                 
                                                 <tr id="second-custom-filters">
                                                    <td>
                                                        <span>Item</span>
                                                        <input 
                                                           type="text" 
                                                           class="item-number validate-alp" 
                                                           autocomplete="off"/>
                                                    </td>
                                                    
                                                    <td>
                                                        <span>Tariff Page</span>
                                                        <input 
                                                            type="text" 
                                                            class="tariff-page validate-alp" 
                                                            autocomplete="off"/> 
                                                    </td>
                                                 </tr>
                                                 
                                             </tbody>
                                             
                                         </table>
                                    </div>
                                </td>
                            </tr>
                    
                            <tr>
                                <td class="tabDetailViewDL left-part">
                                    <div class="searchItemPanel">
                                         <table class="">
                                            <tr>
                                                <td width="30%">Code</td>
                                                <td>
                                                   <s:textfield cssClass="code validate-alp" autocomplete="off"></s:textfield>
                                                   <i class="help-trigger hint--info hint--top-right hint--rounded" 
                                                      data-hint="Search in USOC and Line Item Code fields."></i>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Rate</td>
                                                <td>
                                                    <s:textfield cssClass="rate validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>

                                            <tr>
                                                 <td width="30%">Rate Status</td><!-- 模糊查询 -->
                                                 <td class="rate-status-list">

                                                     <select name="rate-status-filter-list" id="rate-status-filter-list" style="width: 230px;">
                                                          <option value="" selected></option>
                                                          <option value="active">Active</option>
                                                          <option value="inactive">Inactive</option>  
                                                      </select>
                                                 </td>
                                             </tr>
                                             
                                             <tr>
                                                <td width="30%">Charge Type</td><!-- 模糊查询 -->
                                                <td>
                                                    <select name="charge-type-filter-list" id="charge-type-list" style="width: 230px;">
                                                       <option value="all">All</option>
                                                       <option value="MRC">MRC</option>
                                                       <option value="OCC">OCC</option>
                                                       <option value="Usage">Usage</option>    
                                                    </select>
                                                </td>
                                            </tr>
                                            
                                        </table>
                                        
                                    </div>
                                </td>
                                
                                <td class="tabDetailViewDL">
                                    <div class="searchItemPanel">
                                        <table class="">
                                            <tbody>
                                            
                                                <tr>
                                                    <td width="30%">Description</td>
                                                    <td>
                                                       <s:textfield cssClass="description validate-alp" autocomplete="off"></s:textfield>
                                                       <i class="help-trigger hint--info hint--top-right hint--rounded" 
                                                          data-hint="Search in Line Item Code Description, USOC Long Description and Item Description fields."></i>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Item Type</td>
                                                    <td>
                                                        <s:textfield cssClass="item-type validate-alp" autocomplete="off"></s:textfield>
                                                    </td>
                                                </tr>
                                               

                                                <tr>
                                                    <td width="30%">Effective Date</td>
                                                    <td nowrap="nowrap" class="date-controller-td">
                                                        <input id="rateEffectiveDate" cssClass="validate-date-ca" class="Clear1 Date"/>

                                                        <a onclick="showCalendarController('rateEffectiveDate')" href="javascript:void(0);" >
                                                            <img src="include/images/cal.gif" id="effectiveDateImg" border="0" align="middle">
                                                        </a>
                                                        <span class="calendar-label">yyyy/mm/dd</span>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td>Expiration Period</td>
                                                    <td>
                                                        <select id="expiration-period-list" style="width: 230px;" disabled="disabled">
                                                            <option value=""></option>
                                                            <option value="Within 6 months"s>Within 6 months</option>
                                                            <option value="6 - 12 months">6 - 12 months</option>
                                                            <option value="13 - 24 months">13 - 24 months</option>
                                                            <option value="25 - 36 months">25 - 36 months</option>
                                                            <option value="37 - 60 months">37 - 60 months</option>
                                                            <option value="> 60 months">&gt; 60 months</option>
                                                            <option value="Expired">Expired</option>           
                                                         </select>
                                                    </td>
                                                </tr>

                                                

                                                  
                                                
                                                
                                            </tbody>
                                        </table>
                                       
                                    </div>
                                </td>
                            </tr>        
                        </tbody>
                    </table>
                </s:form>
                
                <!-- 按钮组 -->
                <div class="buttons-container clearfix">
                    <div class="left-buttons pull-left">
                        <input class="search--button button-item" type="button" value="Search" onclick="startSearch()">
                        <input class="clear--button button-item" type="button" value="Clear" onclick="resetRateSearchFormElementValue()">
                    </div>
                    <div class="right-buttons clearfix" style="float: right; width: 238px;">

                        <!-- 选择 download 空模板 -->
                        <input type="button" value="Download Template" onclick="rateBlankTemplateObj.popupDownloadBlankRateTemplateNotes()" style=" float: left;
                                height: 23px;
                                margin-right: 5px;
                                line-height: 18px;">

                        <form id="uploadRateForm" enctype="multipart/form-data" method="post" action="uploadRate.action" target="ratePageFrame">
							<div  id="attachDiv">
								<input id="rateUpload" type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files)" />
								<div class="quote-attachBorder quote-upload-div">Upload</div>
							</div>
                        </form>
                        <iframe name="ratePageFrame" id="ratePageFrame" style="display: none" src="javascript:false"></iframe>
                    </div>
                </div>
            </div>
            
            <!-- Rate search switch tab. -->
            <div id="rateSearchSwitchTabContainer" style="display: none;">
               
                
                <!-- 导航内容 -->
                <div class="" id="rateSearchDetailsContent">
                    <!-- Rate Search Details -->
                    <div id="rateSearchWrapper" class="list-wrapper">
                        <table class="wrapper-inner-table">
                            <tr>
                                <td>
                                    <div class="data-table-wrapper">
                                        <div id="rate-search-data-table"></div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="list-pagination-TD">
                                    <div id="rateSearch-dataTable-page" class="pagination-table"></div>
                                    <div class="oper-buttons">
                                        <input type="button" id="downloadRateSearchRecordsToExcel" value="Download to Excel" onclick="downloadRateSearchRecordsToExcel()" style="display: none;" />
                                    </div>
                                    <div class="oper-buttons">
                                        <input type="button" id="downloadRateTemplateToExcel" value="Download Update Data Template" onclick="downloadRateTemplateToExcel()" style="display: none;" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

                <div class="" id="contractSummaryContent" >
                    <!-- Contract Summary Details -->
                    <div id="contractSummaryWrapper" class="list-wrapper">
                        <table class="wrapper-inner-table">
                            <tr>
                                <td>
                                    <div class="data-table-wrapper">
                                        <div id="contract-summary-data-table" ></div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="list-pagination-TD">
                                    <div id="contractSummary-dataTable-page" class="pagination-table"></div>
                                    <div class="oper-buttons">
                                        <input type="button" id="downloadContractSummaryRecordsToExcel" value="Download to Excel" onclick="downloadContractSummaryRecordsToExcel()" style="display: none;" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>

            </div>

            

            
            
            

        </div>
    </div>
    <div class="yui-pe-content" id="rateWindow" style="visibility: hidden;">
    	<div class="hd">
    		<h2>Rate</h2>
    	</div>
    	<div class="tabForm" style="padding-bottom:25px;  height:100px;">
    		<div class="rateWindow-left">
    			<div class="failed-icon"></div>
    		</div>
    		<div class="rateWindow-right">
    			<div class="title">Rate file uploaded is failed.</div>
    			<div class="content">Some data is incorrect,please download the error file.</div>
    			<div class="link" onclick="downLoadRateResult()">Rate File Import Error(s).xlsx</div>
    		</div>
        </div>
    </div>
    
    <!-- Download template notes. -->
    <div class="yui-pe-content" id="download-template-notes-window" style="visibility: hidden;">
        <div class="hd">
            <h2>Download Template Notes</h2>
        </div>
        <div class="tabForm" style="padding-bottom:25px;">
             <div class="content" style="line-height: 18px;">
                If upload new data in TEMS, please choose below template.  When you fill data use the template please leave the ID column is blank, select Key Field from Dropdown List. If the Key Field is not in Dropdown List, Please send the document to <em>supportrogers@saninco.com</em>.

                <div class="download-template-btn-group" style="margin: 20px 0;">
                    <input type="button" value="Download Tariff Template" style="margin-right: 5px; cursor: pointer;" 
                    onclick="rateBlankTemplateObj.selectToDownloadBlankRateTemplate('Tariff')">
                    <input type="button" value="Download Contract Template" style="margin-right: 5px; cursor: pointer;" 
                    onclick="rateBlankTemplateObj.selectToDownloadBlankRateTemplate('Contract')">
                    <input type="button" value="Download MtM Template" 
                    onclick="rateBlankTemplateObj.selectToDownloadBlankRateTemplate('MtM')" style="cursor: pointer;">
                </div>

                If change data in TEMS , please return to Rate Tab, choose one rate type from  <em>Contract / Tariff / MtM</em> field,  and  search data that want to update, then click <em>Download Update Data Template</em> button. When edit the data at excel, ID column data cannot be changed.
            
             </div>
            
            

        </div>
    </div>
</body>
</html>