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
	<title>Contrac</title>
	<link rel="stylesheet" href="include/css/contract/contract.css">
    <script src="include/javascript/contract/contract.js"></script>
    <script>
        // Carrier Entity Name Filter.
        var carrier_entity_name_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${carrierEntityNameList}" var="carrierEntityNameListtMap">
                ,{
                    "id": "${carrierEntityNameListtMap.key}",
                    "name": "${carrierEntityNameListtMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(carrierEntityNameList)}
        };

        $(function(){
            form0_searchContractVO_carrierEntityName = $('#carrier-entity-name-filter-list').flexbox(carrier_entity_name_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_searchContractVO_carrierEntityName.setValue("all");
        });

        // Carrier Type Filter.
        var carrier_type_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${carrierTypeList}" var="carrierTypeListMap">
                ,{
                    "id": "${carrierTypeListMap.key}",
                    "name": "${carrierTypeListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(carrierTypeList)}
        };

        $(function(){
            form0_searchContractVO_carrierType = $('#carrier-type-filter-list').flexbox(carrier_type_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_searchContractVO_carrierType.setValue("all");
        });

        // Agreement Type Filter.
        var agreement_type_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${agreementTypeList}" var="agreementTypeListMap">
                ,{
                    "id": "${agreementTypeListMap.key}",
                    "name": "${agreementTypeListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(agreementTypeList)}
        };

        $(function(){
            form0_searchContractVO_agreementType = $('#agreement-type-filter-list').flexbox(agreement_type_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_searchContractVO_agreementType.setValue("all");
        });

        // Products/Services Filter.
        var products_services_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${productsServicesList}" var="productsServicesListMap">
                ,{
                    "id": "${productsServicesListMap.key}",
                    "name": "${productsServicesListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(productsServicesList)}
        };

        $(function(){
            form0_searchContractVO_productsServices = $('#products-services-filter-list').flexbox(products_services_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_searchContractVO_productsServices.setValue("all");
        });

        // Term Filter.
        // 这里的term是数据库中的term_combined字段
        var term_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${termCombinedList}" var="termCombinedListMap">
                ,{
                    "id": "${termCombinedListMap.key}",
                    "name": "${termCombinedListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(termCombinedList)}
        };

        $(function(){
            form0_searchContractVO_term = $('#term-combined-filter-list').flexbox(term_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_searchContractVO_term.setValue("all");
        });
    </script>
    
    
</head>
<body>
	<div id="contractPageContainer" class="tabForm">
		<h3 class="page-title">Contract Search</h3>

        <div class="contract-content">
            <div class="search-module">
                <s:form id="form0" action="" onsubmit="">
                    <table class="tabDetailView">
                        <tbody>
                            <tr>
                                <td class="tabDetailViewDL left-part" width="52%">
                                    <div class="searchItemPanel">
                                        <table class="main-filter-conditions">
                                            <tr>
                                                <td width="30%">Internal ID #</td>
                                                <td>
                                                    <s:textfield cssClass="internal-id-number validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Supplier Master Contract #</td>
                                                <td>
                                                    <s:textfield cssClass="supplier-master-contract-number validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Name of Agreement</td>
                                                <td>
                                                    <s:textfield cssClass="name-of-agreement validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                
                                <td class="tabDetailViewDL">
                                    <div class="searchItemPanel">
                                         <table class="">
                                            <tr>
                                                <td width="30%">Amendment Date</td>
                                                <td nowrap="nowrap" class="date-controller-td">
                                                    <input id="searchContractVO_amendmentDate" cssClass="validate-date-ca" class="Clear1 Date"/>

                                                    <a onclick="showCalendarController('searchContractVO_amendmentDate')" href="javascript:void(0);" class="calendar-image-link">
                                                        <img src="include/images/cal.gif" id="amendmendDateImg" border="0" align="middle">
                                                    </a>
                                                    <span class="calendar-label">yyyy/mm/dd</span>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Carrier Entity Name</td>
                                                <td class="carrier-entity-name-list">
                                                    <div id="carrier-entity-name-filter-list"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Carrier Address</td>
                                                <td>
                                                    <s:textfield cssClass="carrier-address validate-alp"></s:textfield>
                                                </td>
                                            </tr>

                                            <tr>
                                                <td>Rogers Legal Entity Name</td>
                                                <td>
                                                    <s:textfield cssClass="rogers_legal_entity_name validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            
                                        </table>
                                    </div>
                                </td>
                            </tr>
                    
                            <tr>
                                <td class="tabDetailViewDL left-part">
                                    <div class="searchItemPanel">
                                        <table class="">
                                            <tr>
                                                <td width="30%">Carrier Type</td>
                                                <td class="carrier-type-list">
                                                    <div id="carrier-type-filter-list"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Agreement Type</td>
                                                <td class="agreement-type-list">
                                                    <div id="agreement-type-filter-list"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Products/Services</td>
                                                <td class="products-services-list">
                                                    <div id="products-services-filter-list"></div>
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
                                                    <td width="30%">Schedules</td>
                                                    <td>
                                                        <s:textfield cssClass="schedules validate-alp"></s:textfield>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>TERM</td>
                                                    <td class="term-combined-list">
                                                        <div id="term-combined-filter-list"></div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                   <td>Contract Signed Date</td>
                                                    <td nowrap="nowrap" class="date-controller-td">
                                                        <input id="searchContractVO_contractSignedDate" cssClass="validate-date-ca" class="Clear1 Date"/>

                                                        <a onclick="showCalendarController('searchContractVO_contractSignedDate')" href="javascript:void(0);" class="calendar-image-link">
                                                            <img src="include/images/cal.gif" id="contractSignedDateImg" border="0" align="middle">
                                                        </a>
                                                        <span class="calendar-label">yyyy/mm/dd</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                   <td>Term - Expiry</td>
                                                    <td nowrap="nowrap" class="date-controller-td">
                                                        <input id="searchContractVO_termExpiry" cssClass="validate-date-ca" class="Clear1 Date"/>

                                                        <a onclick="showCalendarController('searchContractVO_termExpiry')" href="javascript:void(0);" class="calendar-image-link">
                                                            <img src="include/images/cal.gif" id="termExpiryImg" border="0" align="middle">
                                                        </a>
                                                        <span class="calendar-label">yyyy/mm/dd</span>
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
                        <input class="search-contract--button button-item" type="button" value="Search" onclick="searchContract()">
                        <input class="clear-contract--button button-item" type="button" value="Clear" onclick="resetContractFormElementValue()">
                    </div>
                    <div class="right-buttons pull-right">

                        <input class="penalty-calculator--button button-item" type="button" value="Penalty Calculator" onclick="showTerminationPenaltyPopup()">
                    </div>
                </div>
            </div>

            

            <!-- Contract 搜索结果表格。 -->
            <div id="contract-wrapper" class="contract-list list-wrapper"><br/>
                <table class="wrapper-inner-table">
                    <tr>
                        <td>
                            <div class="data-table-wrapper">
                                <div id="contract-data-table"></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td class="contract-pagination-TD">
                            <div id="contract-dataTable-page"></div>
                            <div class="oper-buttons">
                                <input type="button" id="downloadContractRecordsToExcel" value="Download to Excel" onclick="downloadContractRecordsToExcel()" style="display: none;" />
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            
            

        </div>
	</div>
    <!-- Termination penalty -->
    <div class="termination-penalty-popup">
        <div class="termination-penalty">
            <div class="termination-penalty-title">
                <span class="termination-penalty-title-text">Termination Penalty Calculator</span>
                <i class="close-penalty-popup-icon"></i>
            </div>

            <div class="termination-penalty-content">
                <div>
                    <div class="search-conditions">
                        <table class="search-conditions-table">
                            <tr>
                                <td width="24%">Circuit Number</td>
                                <td>
                                    <s:textfield cssClass="circuit-number validate-alp"></s:textfield>
                                </td>
                            </tr>
                            <tr>
                                <td>Termination Date</td>
                                <td>
                                    <input id="terminationDate" cssClass="validate-date-ca" class="Clear1 Date"/>

                                    <a onclick="showCalendarController('terminationDate')" href="javascript:%20void(0);" class="calendar-image-link">
                                        <img src="include/images/cal.gif" id="terminationDateImg" border="0" align="middle">
                                    </a>
                                    <span class="calendar-label">yyyy/mm/dd</span>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="buttons-container">
                        <input type="button" value="Submit" class="penalty-submit-button" onclick="searchTerminationPenalty()">
                        <input type="button" value="cancel" class="penalty-cancel-button" onclick="cancelTerminationPenalty()">
                    </div>
                    <div id="penalty_list_div" style="padding-top: 20px;">
						<table style="width:100%;table-layout: fixed;">
							<tr>
								<td>
								<div align="left"  style="width:100%;overflow-x:auto;border: 0px solid;padding-bottom: 20px;">
									<div id="_penalty_list"></div>
								</div>
								</td>
							</tr>
						</table>
						<table style="width:100%;">
							<tr>
								<td>
									<div class="demo" id="_penalty_page_list" style="float:left"></div>
								</td>
							<tr>
						</table>
					</div>
                </div>

                <div id="termination-penalty-table-container">
                    <table class="termination-penalty-table">
                        <tr>
                            <td>
                                <div class="penalty-data-table-wrapper">
                                    <div id="termination-penalty-data-table"></div>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</body>
</html>