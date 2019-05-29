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
    <meta name="description" content="Contract验证规则列表">
    <title>Contract Rules</title>
    <link rel="stylesheet" href="include/css/contractAndTariffRules/contractRules.css">
    <script src="include/javascript/contractAndTariffRules/contractRules.js"></script>
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
            form0_summaryVendorName_dropdownList = $('#summary-vendor-name-filter-list').flexbox(summary_vendor_name_array, {
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
                width : 327,
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
    <div id="contractRulesPageContainer" class="tabForm">
        <h3 class="page-title">Contract Rules Search</h3>

        <div class="contract-rules-content">
            <div class="search-module">
                <s:form id="form0" action="" onsubmit="">
                    <table class="tabDetailView">
                        <tbody>
                            <tr>
                                <td class="tabDetailViewDL left-part" width="52%">
                                    <div class="searchItemPanel">
                                        <table class="main-filter-conditions">
                                            <tr>
                                                <td width="30%">USOC</td>
                                                <td>
                                                    <s:textfield cssClass="usoc validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>USOC Long Description</td>
                                                <td>
                                                    <s:textfield cssClass="usoc-long-description validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Stripped Circuit Number</td>
                                                <td>
                                                    <s:textfield cssClass="stripped-circuit-number validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Expiration Period</td>
                                                <td>
                                                    <select name="" class="expiration-period">
                                                        <option value=""></option>
                                                        <option value="withinSixMonths">Within 6 months</option>
                                                        <option value="sixToTwelveMonths">6 - 12 months</option>
                                                        <option value="thirteenToTwentyFourMonths">13 - 24 months</option>
                                                        <option value="Expired">Expired</option>
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
                                                     <td width="30%">Sub Product</td>
                                                     <td class="sub-product-list">
                                                         <div id="sub-product-filter-list"></div>
                                                     </td>
                                                 </tr>
                                                 <tr>
                                                     <td>Contract Name</td>
                                                     <td class="contract-name-list">
                                                         <select id="contract-name-filter-list" name="contract-name-filter-list" style="width: 230px;">
                                                             <option value="all">All</option>
                                                             <c:forEach items="${contractNameList}" var="contractNameListMap">
                                                                <option value="${contractNameListMap.key}">${contractNameListMap.value}</option>
                                                            </c:forEach>
                                                         </select>
                                                     </td>
                                                 </tr>

                                                 <tr>
                                                     <td>Rate</td>
                                                     <td>
                                                         <s:textfield cssClass="rate validate-alp" autocomplete="off"></s:textfield>
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
                                            <tbody>
                                                <tr>
                                                    <td width="30%">Summary Vendor Name</td><!-- 模糊查询 -->
                                                    <td class="summary-vendor-name-list">
                                                        <div id="summary-vendor-name-filter-list"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>Key Field</td>
                                                    <td class="key-field-list">
                                                        <div id="key-field-filter-list"></div>
                                                    </td>
                                                </tr>

                                                <tr>
                                                    <td width="30%">Effective Date</td>
                                                    <td nowrap="nowrap" class="date-controller-td">
                                                        <input id="rateEffectiveDate" cssClass="validate-date-ca" class="Clear1 Date"/>

                                                        <a onclick="showCalendarController('rateEffectiveDate')" href="javascript:void(0);">
                                                            <img src="include/images/cal.gif" id="effectiveDateImg" border="0" align="middle">
                                                        </a>
                                                        <span class="calendar-label">yyyy/mm/dd</span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                            
                                        </table>
                                    </div>
                                </td>
                                
                                <td class="tabDetailViewDL">
                                    <div class="searchItemPanel">
                                         <table class="">
                                            <tr>
                                                <td width="30%">Item Description</td>
                                                <td>
                                                   <s:textfield cssClass="item-description validate-alp" autocomplete="off"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Line Item Code</td>
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
                        <input class="search-contract-rules--button button-item" type="button" value="Search" onclick="searchContractRulesList()">
                        <input class="clear-contract-rules--button button-item" type="button" value="Clear" onclick="resetContractFormElementValue()">
                    </div>
                    
                </div>
            </div>

            <div id="summary-and-details" class="yui-navset">
                
                <ul class="yui-nav" id="summary-and-details-nav">
                    <li id="contractRulesDetails"  class="selected"><a href="javascript: void(0)"><em>Details</em></a></li>
                    <li id="contractRulesSummary" ><a href="javascript: void(0)"><em>Summary</em></a></li>
                </ul>

                <div class="yui-content details-content">
                                    
                    <div id="contractRulesSearchResultTableWrapper" class="contract-rules-list list-wrapper"><br/>
                        <table class="wrapper-inner-table">
                            <tr>
                                <td>
                                    <div class="data-table-wrapper">
                                        <div id="contract-rules-data-table"></div>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td class="contract-rules-pagination-TD">
                                    <div id="contract-rules-dataTable-page"></div>
                                    <div class="oper-buttons">
                                        <input type="button" id="downloadContractRulesRecordsToExcel" value="Download to Excel" 
                                        onclick="downloadContractRulesRecordsToExcel()" style="display: none;" />
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    
                </div>

                <div class="yui-content summary-content" style="display: none">
                   <div id="contractRulesSummarySearchResultTableWrapper" class="contract-rules-list list-wrapper"><br/>
                       <table class="wrapper-inner-table">
                           <tr>
                               <td>
                                   <div class="data-table-wrapper">
                                       <div id="contract-rules-summary-data-table"></div>
                                   </div>
                               </td>
                           </tr>
                           <tr>
                               <td class="contract-rules-pagination-TD">
                                   <div id="contract-rules-summary-dataTable-page">This is summary table.</div>
                                   <div class="oper-buttons">
                                       <input type="button" id="downloadContractRulesSummaryRecordsToExcel" value="Download to Excel" 
                                       onclick="downloadContractRulesSummaryRecordsToExcel()" style="display: none;" />
                                   </div>
                               </td>
                           </tr>
                       </table>
                   </div>
                </div>

            </div>

            <!-- Contract Rules 搜索结果表格。 -->
            
            
            

        </div>
    </div>
   
</body>
</html>