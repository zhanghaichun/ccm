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
    <meta name="description" content="mtm验证规则列表">
    <title>MtM Rules</title>
    <link rel="stylesheet" href="include/css/contractAndTariffRules/mtmRules.css">
    <script src="include/javascript/contractAndTariffRules/mtmRules.js"></script>
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
	
	    $(function(){
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
	    });

	    
	    
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

	    $(function(){
	        form0_vendorName_dropdownList = $('#vendorNameFilterList').flexbox(vendor_name_array, {
	            maxCacheBytes : 200000,
	            highlightMatches : true,
	            autoCompleteFirstMatch : false,
	            paging: false,
	            width : 260,
	            onSelect: function(){
	            }
	        });
	        form0_vendorName_dropdownList.setValue("all");
	    });

        // Key Field Filter.
        var key_field_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${keyFieldList}" var="keyFieldListMap">
                ,{
                    "id": "${keyFieldListMap.key}",
                    "name": "${keyFieldListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(keyFieldList)}
        };

        $(function(){
            form0_keyField_dropdownList = $('#key-field-filter-list').flexbox(key_field_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 297,
                onSelect: function(){
                }
            });
            form0_keyField_dropdownList.setValue("all");
        });

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

        $(function(){
            form0_subProduct_dropdownList = $('#sub-product-filter-list').flexbox(sub_product_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_subProduct_dropdownList.setValue("all");
        });

    </script>
    
    
</head>
<body>
    <div id="mtmRulesPageContainer" class="tabForm">
        <h3 class="page-title">MtM Rules Search</h3>

        <div class="mtm-rules-content">
            <div class="search-module">
                <s:form id="form0" action="" onsubmit="">
                    <table class="tabDetailView">
                        <tbody>
                            <tr>
                                <td class="tabDetailViewDL left-part" width="52%">
                                    <div class="searchItemPanel">
                                        <table class="main-filter-conditions">
                                            <tr>
                                                <td width="30%">Summary Vendor Name</td><!-- 模糊查询 -->
                                                <td class="summary-vendor-name-list">
                                                    <div id="summaryVendorNameFilterList"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Stripped Circuit Number</td><!-- 模糊查询 -->
                                                <td>
                                                    <s:textfield cssClass="stripped-circuit-number validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Rate</td>
                                                <td>
                                                    <s:textfield cssClass="rate validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Rate Effective Date</td>
                                                <td>
													<s:textfield cssClass="rate-effective-date validate-date-ca" id = "rate-effective-date"></s:textfield>
													<a
														onClick="g_Calendar.show(event,'rate-effective-date',false, 'yyyy/mm/dd', new Date(1990,1,1)); return false;"
														href="javascript:%20void(0);"><img style="padding:5px"
															src="include/images/cal.gif"
															border="0" align="middle"></a>
															yyyy/mm/dd
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
                                                    <td>Sub Product</td>
                                                    <td class="sub-product-list">
                                                        <div id="sub-product-filter-list"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Rule Key Field</td>
                                                    <td class="key-field-list">
                                                        <div id="key-field-filter-list"></div>
                                                    </td>
                                                </tr>
                                                 <tr>
                                                     <td width="30%">Charge Type</td><!-- 模糊查询 -->
                                                     <td class="charge-type-list">

                                                         <select name="charge-type-filter-list" id="charge-type-filter-list" style="width: 230px;">
                                                             <option value="all">All</option>
                                                             <c:forEach items="${chargeTypeList}" var="chargeTypeListMap">
                                                                <option value="${chargeTypeListMap.key}">${chargeTypeListMap.value}</option>
                                                             </c:forEach>   
                                                         </select>
                                                     </td>
                                                 </tr>
                                                 <tr>
                                                     <td width="30%">Rate Status</td>
                                                     <td class="charge-type-list">

                                                         <select name="rate-status-filter-list" id="rate-status-filter-list" style="width: 230px;">
                                                             
                                                             <option value="" selected></option>
                                                             <option value="active">Active</option>
                                                             <option value="inactive">Inactive</option>
                                                         </select>
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
                                                <td width="30%">USOC</td>
                                                <td>
                                                   <s:textfield cssClass="usoc validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>USOC Description</td>
                                                <td>
                                                    <s:textfield cssClass="usoc-description validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Item Description</td>
                                                <td>
                                                    <s:textfield cssClass="item-description validate-alp" autocomplete="off"></s:textfield>
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
                                                    <td width="30%">Line Item Code</td>
                                                    <td>
                                                        <s:textfield cssClass="line-item-code validate-alp" autocomplete="off"></s:textfield>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Line Item Code Description</td>
                                                    <td>
                                                        <s:textfield cssClass="line-item-code-description validate-alp" autocomplete="off"></s:textfield>
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
                        <input class="search-mtmRules--button button-item" type="button" value="Search" onclick="searchMtMRulesList()">
                        <input class="clear-mtmRules--button button-item" type="button" value="Clear" onclick="resetContractFormElementValue()">
                    </div>
                    
                </div>
            </div>

            

            <!-- MtM Rules 搜索结果表格。 -->
            <div id="mtmRulesSearchResultTableWrapper" class="mtm-rules-list list-wrapper"><br/>
                <table class="wrapper-inner-table">
                    <tr>
                        <td>
                            <div class="data-table-wrapper">
                                <div id="mtm-rules-data-table"></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="mtmRules-pagination-TD">
                            <div id="mtmRules-dataTable-page"></div>
                            <div class="oper-buttons">
                                <input type="button" id="downloadMtMRulesRecordsToExcel" value="Download to Excel" onclick="downloadMtMRulesRecordsToExcel()" style="display: none;" />
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            
            

        </div>
    </div>
</body>
</html>