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
	<title>Quote Inventory</title>

	<meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="Quote Inventory">
    
    <!-- simpleCalendar.css -->
    <link rel="stylesheet" type="text/css" href="include/javascript/Calendar/styles/calendar.css">
    <!-- 前台过滤条件中下拉列表的样式 -->
    <link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css">
    <link rel="stylesheet" type="text/css" href="include/css/hint.min.css">
    <link rel="stylesheet" type="text/css" href="include/css/CTPList.css">
    <link rel="stylesheet" href="include/css/quoteInventory/quote_inventory.css">

    
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
    <script src="include/javascript/quoteInventory/quote_inventory.js"></script>

    <script>
     
       /**
        * Quote Inventory 中的上半部分的两个下拉列表中的过滤条件,
        * provider list 和 product list 下拉列表。
        * 需要动态的加载过滤信息数据。
        */

        // Provider Filter.
        var provider_array = {
            "results": [
                {
                    "id": "all",
                    "name": ""
                }
                <c:forEach items="${providerList}" var="providerListMap">
                ,{
                    "id": "${providerListMap.key}",
                    "name": "${providerListMap.value}"
                }
                </c:forEach>
            ],
            "total": ${fn:length(providerList)}
        };

        $(function(){
            form0_searchQuoteVO_providerName = $('#provider-filter-list').flexbox(provider_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect: function(){
                }
            });
            form0_searchQuoteVO_providerName.setValue("all");
        });
        
        // Product Filter.
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
            form0_searchQuoteVO_productName = $('#product-filter-list').flexbox(product_array, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 230,
                onSelect : function(){
                }
            });
            form0_searchQuoteVO_productName.setValue("all");
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
        <h3 class="page-title">Quote Inventory</h3>

        <div class="quote-inventory-content">
            <div class="search-module">
                <s:form id="form0" action="" onsubmit="">
                    <table class="tabDetailView">
                        <tbody>
                            <tr>
                                <td class="tabDetailViewDL left-part" width="52%">
                                    <div class="searchItemPanel">
                                        <table class="main-filter-conditions">
                                            <tr>
                                                <td width="30%">Compare</td>
                                                <td>
                                                    <input type="checkbox" name="searchContractTariffPriceListVO.isCompareFilter" class="compare-checkbox" onclick="compareFilters(this)">
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Provider</td>
                                                <!-- 这里的Provider, 实际上就是数据库中的 Vendor -->
                                                <td class="provider-list">
                                                    <div id="provider-filter-list"></div>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Product</td>
                                                <td class="product-list">
                                                    <div id="product-filter-list"></div>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                
                                <td class="tabDetailViewDL compare-table-td">
                                    <div class="searchItemPanel">
                                         <table class="compare-filters-table">
                                            <tr>
                                                <td width="30%">Record ID</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.recordId" cssClass="record-id validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Zendesk Quote ID</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.zendeskQuoteId" cssClass="zendesk-quote-id validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Enterprise Customer</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.enterpriseCustomer" cssClass="enterprise-customer validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            
                                        </table>
                                    </div>
                                </td>
                            </tr>
                    
                            <tr>
                                <td class="tabDetailViewDL left-part">
                                    <div class="searchItemPanel">
                                        <table class="second-filter-conditions">
                                            <tr>
                                                <td width="30%">A Street Number</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.aStreetNumber" cssClass="a-street-number validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>A Street Name</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.aStreetName" cssClass="a-street-name validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>A City</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.aCity" cssClass="a-city validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>A Province</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.aProvince" cssClass="a-province validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>A Postal Code</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.aPostalCode" cssClass="a-postal-code validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Z Street Number</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.zStreetNumber" cssClass="z-street-number validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Z Street Name</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.zStreetName" cssClass="z-street-name validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Z City</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.zCity" cssClass="z-city validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Z Province</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.zProvince" cssClass="z-province validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td>Z Postal Code</td>
                                                <td>
                                                    <s:textfield name="searchQuoteVO.zPostalCode" cssClass="z-postal-code validate-alp"></s:textfield>
                                                </td>
                                            </tr>
                                        </table>
                                    </div>
                                </td>
                                
                                <td class="tabDetailViewDL date-filters-td compare-table-td">
                                    <div class="searchItemPanel date-item-panel">
                                        <table class="date-received-table">
                                            <tbody>
                                                <tr>
                                                    <td class="date-received checkbox-container" colspan="3">
                                                        <span class="date-title">Date Received</span>
                                                        <input type="checkbox" id="quote-date-received" name="lockBox" class="switch-checkbox" onclick="clearDisabled('quote-date-received',1);">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="11%">
                                                        <input type="radio" id="quote-date-received-radio" class="noBorderRadioButton" name="quote-date-received-radio" checked="checked" >
                                                    </td>
                                                    <td width="17%">From</td>
                                                    <td nowrap="nowrap" class="date-controller-td">
                                                        <input id="searchQuoteVO_dateReceivedStartOn" name="searchQuoteVO.dateReceivedStartOn" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled"/>

                                                        <a onclick="calendarController('quote-date-received', 'searchQuoteVO_dateReceivedStartOn')" href="javascript:%20void(0);" class="calendar-image-link">
                                                            <img src="include/images/cal.gif" id="dateReceivedStartOnImg" border="0" align="middle">
                                                        </a>
                                                        <span class="calendar-label">yyyy/mm/dd</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                    <td>To</td>
                                                    <td class="date-controller-td">
                                                        <input id="searchQuoteVO_dateReceivedEndOn" name="searchQuoteVO.dateReceivedEndOn" cssClass="validate-date-ca" class="Clear1 Date" disabled="disabled"/>

                                                        <a onclick="calendarController('quote-date-received', 'searchQuoteVO_dateReceivedEndOn')" href="javascript:%20void(0);" class="calendar-image-link">
                                                            <img src="include/images/cal.gif" id="dateReceivedEndOnImg" border="0" align="middle">
                                                        </a>
                                                        <span class="calendar-label">yyyy/mm/dd</span>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                        <hr class="table-seperator">
                                        <table class="date-issued-table">
                                            <tbody>
                                                <tr>
                                                    <td class="date-issued checkbox-container" colspan="3">
                                                        <span class="date-title">Date Issued</span>
                                                        <input type="checkbox" id="quote-date-issued" name="lockBox" class="switch-checkbox" onclick="clearDisabled('quote-date-issued',2);">
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td width="11%">
                                                        <input type="radio" id="quote-date-issued-radio" class="noBorderRadioButton" name="quote-date-issued-radio" checked="checked" >
                                                    </td>
                                                    <td width="17%">From</td>
                                                    <td nowrap="nowrap" class="date-controller-td">
                                                        <input id="searchQuoteVO_dateIssuedStartOn" name="searchQuoteVO.dateIssuedStartOn" cssClass="validate-date-ca" class="Clear2 Date" disabled="disabled"/>

                                                        <a onclick="calendarController('quote-date-issued', 'searchQuoteVO_dateIssuedStartOn')" href="javascript:%20void(0);" class="calendar-image-link">
                                                            <img src="include/images/cal.gif" id="dateIssuedStartOnImg" border="0" align="middle">
                                                        </a>
                                                        <span class="calendar-label">yyyy/mm/dd</span>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td>&nbsp;</td>
                                                    <td>To</td>
                                                    <td class="date-controller-td">
                                                        <input id="searchQuoteVO_dateIssuedEndOn" name="searchQuoteVO.dateIssuedEndOn" cssClass="validate-date-ca" class="Clear2 Date" disabled="disabled"/>

                                                        <a onclick="calendarController('quote-date-issued', 'searchQuoteVO_dateIssuedEndOn')" href="javascript:%20void(0);" class="calendar-image-link">
                                                            <img src="include/images/cal.gif" id="dateIssuedEndOnImg" border="0" align="middle">
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
                    <div class="left-buttons">
                        <input class="search-quote--button button-item" type="button" value="Search" onclick="startSearch()">
                        <input class="save-quote--button button-item" type="button" value="Save" onclick="YAHOO.ccm.container.saveQueryDialog.show()">
                        <input class="clear-quote--button button-item" type="button" value="Clear" onclick="resetFormElementValue()">
                    </div>
                    <div class="right-buttons clearfix">

                        <input class="download-template--button button-item" type="button" value="Download Template" onclick="downloadQuoteInventoryTemplateToExcel()">
                        <!-- uploadMasterInventory -->
                        <form id="uploadUqoteForm" enctype="multipart/form-data" method="post" action="uploadQuote.action" target="quotePageFrame">
							<div  id="attachDiv">
								<input id="quoteUpload" type="file" name="uploads" multiple="multiple" onchange="filesProcess(this.files)" />
								<div class="quote-attachBorder quote-upload-div">Upload</div>
							</div>
                        </form>
                        <iframe name="quotePageFrame" id="quotePageFrame" style="display: none" src="javascript:false"></iframe>
                    </div>
                </div>
            </div>

            <!-- Quote Inventory 搜索结果表格。 -->
            <div id="_gridDiv" style="width:100%;" class="quote-inventory-list"><br/>
                <table border="0" cellpadding="0" cellspacing="0" style="table-layout: fixed; width: 100%;">
                    <tr>
                        <td>
                            <div align="left" style="width:100%; overflow-x: auto; padding-bottom: 20px;">
                                <div id="_dataTable"></div>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div id="_dataTable_page"></div>
                            <div class="download-button">
                                <input type="button" id="downloadToExcel" value="Download to Excel" onclick="downloadQuoteInventoryToExcel()" style="display: none;" />
                            </div>
                        </td>
                    </tr>
                </table>
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

        </div>


    </div>

    <!-- Termination Penalty Notes -->
    <div id="penalty-notes-wrapper" class="yui-pe-content" style="visibility: hidden;">
        <div class="hd">Termination Details</div>
        <div class="bd penalty-notes-content">No penalty notes</div>
    </div>

    <!-- 上传文件失败之后的提示框 -->
    <div class="yui-pe-content" id="quoteWindow" style="visibility: hidden;">
    	<div class="hd">
    		<h2>Quote</h2>
    	</div>
    	<div class="tabForm" style="padding-bottom:25px;  height:100px;">
    		<div class="quoteWindow-left">
    			<div class="failed-icon"></div>
    		</div>
    		<div class="quoteWindow-right">
    			<div class="title">Quote upload is failed</div>
    			<div class="content">Some data is incorrect,please download the error file.</div>
    			<div class="link" onclick="downLoadQuoteResult()">Quote Import Errors.xlsx</div>
    		</div>
        </div>
    </div>

    <!-- 保存 query data 的时候弹出的窗口控件。  -->
    <div class="yui-pe-content" id="saveQueryDialog" style="visibility: hidden;">
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
</body>
</html>