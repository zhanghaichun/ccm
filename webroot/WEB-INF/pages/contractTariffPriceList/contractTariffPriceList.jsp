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
	<title>Contract/Tariff/Price List</title>

	<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Contract/Tariff/Price List">
    
    <!-- simpleCalendar.css -->
    <link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
    <!-- 前台过滤条件中下拉列表的样式 -->
    <link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css">
    <link rel="stylesheet" type="text/css" href="include/css/hint.min.css">
    <link rel="stylesheet" type="text/css" href="include/css/CTPList.css">
    <link rel="stylesheet" href="include/css/contractTariffPriceList/contract_tariff_priceList.css">

    
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
    <!-- Contract, Tariff, Price List 三个列表。 -->
    <script type="text/javascript" src="include/javascript/contractTariffPriceList/CTPList.js"></script>
    <script src="include/javascript/contractTariffPriceList/contract_tariff_priceList.js"></script>

    <script>
     
       /**
        * Contract/Tariff/Price List 中的上半部分的两个下拉列表中的过滤条件,
        * vendor list 和 ban list 下拉列表。
        * 需要动态的加载过滤信息数据。
        */

        // Vendor Filter.
        var vendor_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${vendorList}" var="vendorListMap">
                ,{
                    "id": "${vendorListMap.key}",
                    "name": "${vendorListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(vendorList)}
        };

        $(function(){
            form0_contractTariffPriceList_vendorId = $('#vendor-filter-list').flexbox(vendor_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_contractTariffPriceList_vendorId.setValue("all");
        });
        
        // Ban Filter.
        var ban_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${banList}" var="banListMap">
                ,{
                    "id": "${banListMap.key}",
                    "name": "${banListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(banList)}
        };

        
        $(function(){
            form0_contractTariffPriceList_banId = $('#ban-filter-list').flexbox(ban_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect : function(){
                }
            });
            form0_contractTariffPriceList_banId.setValue("all");
        });

        // Product Filter
        var product_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${productList}" var="productListMap">
                ,{
                    "id": "${productListMap.key}",
                    "name": "${productListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(productList)}
        };

        $(function(){
            form0_contractTariffPriceList_productName = $('#product-filter-list').flexbox(product_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_contractTariffPriceList_productName.setValue("all");
        });

        // Product Component Filter
        var product_component_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${productComponentList}" var="productComponentListMap">
                ,{
                    "id": "${productComponentListMap.key}",
                    "name": "${productComponentListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(productComponentList)}
        };

        $(function(){
            form0_contractTariffPriceList_productComponentName = $('#product-component-filter-list').flexbox(product_component_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_contractTariffPriceList_productComponentName.setValue("all");
        });
        
        // Show save query dialog.
        $(function(){
          
            var handleSubmit = function() {
                var qn = queryName.value;
                if(qn.length > 30){
                    alert("The name is too long. ");
                    return false;
                }
                if(!FIC_checkForm('queryName')) return;
                YAHOO.ccm.container.saveQueryDialog.hide();
                saveSearch();
            };

            var handleCancel = function() {
                queryName.value = ""; 
                this.cancel();
            };
            YAHOO.util.Dom.removeClass("saveQueryDialog", "yui-pe-content");
            
            YAHOO.ccm.container.saveQueryDialog = new YAHOO.widget.Dialog("saveQueryDialog", 
                                    { width : "30em",
                                      fixedcenter : true,
                                      visible : false, 
                                      constraintoviewport : true,
                                      buttons : [ { text:"Submit", handler:handleSubmit},
                                              { text:"Cancel", handler:handleCancel } ]
                                    });
            YAHOO.ccm.container.saveQueryDialog.render();
        });
        
    </script>
</head>
<body>
	<div id="pageContainer" class="tabForm" onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();">
        <h3 class="page-title">Contract / Tariff / Price List</h3>

        <div class="contract-tariff-priceList-content">
            
            <div class="search-module">
                <s:form id="form0" action="" onsubmit="">
                    <table class="tabDetailView">
                        <tbody>
                            <tr>
                                <td class="tabDetailViewDL left-part" width="52%">
                                    <div class="searchItemPanel">
                                        <table class="main-filter-conditions">
                                            <tr>
                                                <td width="30%">File Name</td>
                                                <td>
                                                    <s:textfield name="searchContractTariffPriceListVO.fileName" cssClass="file-name validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Vendor</td>
                                                <td class="vendor-list">
                                                    <div id="vendor-filter-list"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Ban</td>
                                                <td class="ban-list">
                                                    <div id="ban-filter-list"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Circuit Number</td>
                                                <td>
                                                    <s:textfield name="searchContractTariffPriceListVO.circuitNumber" cssClass="circuit-number validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                
                                <td class="tabDetailViewDL">
                                    <div class="searchItemPanel">
                                         <table class="">
                                            <tr>
                                                <td width="30%">Product</td>
                                                <td>
                                                    <div id="product-filter-list"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Product Component</td>
                                                <td>
                                                    <div id="product-component-filter-list"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>USOC</td>
                                                <td>
                                                    <s:textfield name="searchContractTariffPriceListVO.usoc" cssClass="usoc validate-alp"></s:textfield>
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
                    <div class="left-buttons">
                        <input class="search-quote--button button-item" type="button" value="Search" onclick="startSearch()">
                        <input class="save-quote--button button-item" type="button" value="Save" onclick="YAHOO.ccm.container.saveQueryDialog.show()">
                        <input class="clear-quote--buton button-item" type="button" value="Clear" onclick="resetFormElementValue()">
                    </div>
                </div>
            </div>

            <div id="contract-tariff-priceList-records-wrapper">
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
                            <td>
                                <div id="contract-dataTable-page"></div>
                                <div class="download-button">
                                    <input type="button" id="downloadContractRecordsToExcel" value="Download to Excel" onclick="downloadContractRecordsToExcel()" style="display: none;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                
                <!-- Tariff 搜索结果表格。 -->
                <div id="tariff-wrapper" class="tariff-list list-wrapper"><br/>
                    <table class="wrapper-inner-table">
                        <tr>
                            <td>
                                <div class="data-table-wrapper">
                                    <div id="tariff-data-table"></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="tariff-dataTable-page"></div>
                                <div class="download-button">
                                    <input type="button" id="downloadTariffRecordsToExcel" value="Download to Excel" onclick="downloadTariffRecordsToExcel()" style="display: none;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>

                <!-- Price List 搜索结果表格。 -->
                <div id="priceList-wrapper" class="priceList-list list-wrapper"><br/>
                    <table class="wrapper-inner-table">
                        <tr>
                            <td>
                                <div class="data-table-wrapper">
                                    <div id="priceList-data-table"></div>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <div id="priceList-dataTable-page"></div>
                                <div class="download-button">
                                    <input type="button" id="downloadPriceListRecordsToExcel" value="Download to Excel" onclick="downloadPriceListRecordsToExcel()" style="display: none;" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div> <!-- /#contract-tariff-priceList-records-wrapper -->

        </div>
    </div>
    
    <!-- Termination Penalty Notes -->
    <div id="penalty-notes-wrapper" class="yui-pe-content" style="visibility: hidden;">
        <div class="hd">Termination Details</div>
        <div class="bd penalty-notes-content">No penalty notes</div>
    </div>
    
    <!-- 保存 query data 的时候弹出的窗口控件。  -->
    <div class="yui-pe-content" id="saveQueryDialog" >
        <div class="hd">
            Please type in query name
        </div>
        <div class="bd">
            <table width="95%" align="center" border=0>
                <tr>
                    <td style="color: 000">
                        Query Name:
                    </td>
                    <td>
                        <input type="text" name="queryName" id="queryName" class="required">
                    </td>
                </tr>
            </table>
        </div>
    </div>
    <div id="rate_discrepancy_flag" class="rate-discrepancy-bg-div">
		<div class="tabForm rate-discrepancy-div">
			<div class="rate-discrepancy-div-title">
				<h3 id="reference_type_title" class=".rate-discrepancy-div-h">
				</h3>
				<a class="container-close" onclick="closeRateDiscrepancyList()"></a>
			</div>
			<div id="tab6">
				<table style="width:100%;table-layout: fixed;">
					<tr>
						<td>
						<div align="left"  style="width:100%;overflow-x:auto;overflow-y:auto; max-height :400px;border: 0px solid;padding-bottom: 20px;">
							<div id="_rate_discrepancy_list"></div>
						</div>
						</td>
					</tr>
				</table>
				<table style="width:100%;">
					<tr>
						<td>
							<div id="_rate_discrepancy_page_list" style="float:left"></div>
							<input id="download_button" type="button" value="Download to Excel" onclick="saveRateDiscrepancyToExcel()" />
						</td>
					<tr>
				</table>
			</div>
		</div>
	</div>
</body>
</html>