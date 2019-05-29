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
	<title>Pricelist</title>

	<link rel="stylesheet" href="include/css/pricelist/pricelist.css">
    <script src="include/javascript/pricelist/pricelist.js"></script>

    <script>
    	// Band Filter.
    	var band_array = {
    	    "results": [
    	        {
    	            "id": "all",
    	            "name": ""
    	        }
    	        <c:forEach items="${bandList}" var="bandListMap">
    	        ,{
    	            "id": "${bandListMap.key}",
    	            "name": "${bandListMap.value}"
    	        }
    	        </c:forEach>
    	    ],
    	    "total": ${fn:length(bandList)}
    	};

    	$(function(){
    	    form0_searchPricelistVO_band = $('#band-filter-list').flexbox(band_array, {
    	        maxCacheBytes : 200000,
    	        highlightMatches : true,
    	        autoCompleteFirstMatch : false,
    	        paging: false,
    	        width : 230,
    	        onSelect: function(){
    	        }
    	    });
    	    form0_searchPricelistVO_band.setValue("all");
    	});

    	// Service Type Filter.
    	var service_type_array = {
    	    "results": [
    	        {
    	            "id": "all",
    	            "name": ""
    	        }
    	        <c:forEach items="${serviceTypeList}" var="serviceTypeListMap">
    	        ,{
    	            "id": "${serviceTypeListMap.key}",
    	            "name": "${serviceTypeListMap.value}"
    	        }
    	        </c:forEach>
    	    ],
    	    "total": ${fn:length(serviceTypeList)}
    	};

    	$(function(){
    	    form0_searchPricelistVO_serviceType = $('#service-type-filter-list').flexbox(service_type_array, {
    	        maxCacheBytes : 200000,
    	        highlightMatches : true,
    	        autoCompleteFirstMatch : false,
    	        paging: false,
    	        width : 230,
    	        onSelect: function(){
    	        }
    	    });
    	    form0_searchPricelistVO_serviceType.setValue("all");
    	});
    </script>

</head>
<body>
	<div id="pricelistPageContainer" class="tabForm">
		<h3 class="page-title">Price List Search</h3>

		 <div class="pricelist-content">
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
		 	                                        <s:textfield cssClass="file-name validate-alp"></s:textfield>
		 	                                    </td>
		 	                                </tr>
		 	                                <tr>
		 	                                    <td>Service Type</td>
		 	                                    <td class="service-type-list">
		 	                                        <div id="service-type-filter-list"></div>
		 	                                    </td>
		 	                                </tr>
		 	                            </table>
		 	                        </div>
		 	                    </td>
		 	                    
		 	                    <td class="tabDetailViewDL">
		 	                        <div class="searchItemPanel">
		 	                             <table class="">
		 	                             	<tr>
		 	                                    <td width="30%">Band</td>
		 	                                    <td class="band-list">
		 	                                        <div id="band-filter-list"></div>
		 	                                    </td>
		 	                                </tr>
		 	                                <tr>
		 	                                    <td>Effective Date</td>
		 	                                    <td nowrap="nowrap" class="date-controller-td">
		 	                                        <input id="searchPricelistVO_effectiveDate" cssClass="validate-date-ca" class="Clear1 Date"/>

		 	                                        <a onclick="showCalendarController('searchPricelistVO_effectiveDate')" href="javascript:void(0);" class="calendar-image-link">
		 	                                            <img src="include/images/cal.gif" id="effectiveDateImg" border="0" align="middle">
		 	                                        </a>
		 	                                        <span class="calendar-label">yyyy/mm/dd</span>
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
		 	            <input class="search-pricelist--button button-item" type="button" value="Search" onclick="searchPricelist()">
		 	            <input class="clear-pricelist--button button-item" type="button" value="Clear" onclick="resetPricelistFormElementValue()">
		 	        </div>
		 	       
		 	    </div>
		 	</div>

		 	<!-- pricelist 搜索结果表格。 -->
		 	<div id="pricelist-wrapper" class="pricelist-list list-wrapper"><br/>
		 	    <table class="wrapper-inner-table">
		 	        <tr>
		 	            <td>
		 	                <div class="data-table-wrapper">
		 	                    <div id="pricelist-data-table"></div>
		 	                </div>
		 	            </td>
		 	        </tr>
		 	        <tr>
		 	            <td class="pricelist-pagination-TD">
		 	                <div id="pricelist-dataTable-page"></div>
		 	                <div class="oper-buttons">
		 	                    <input type="button" id="downloadPricelistRecordsToExcel" value="Download to Excel" onclick="downloadPricelistRecordsToExcel()" style="display: none;" />
		 	                </div>
		 	            </td>
		 	        </tr>
		 	    </table>
		 	</div>


		 </div>
	</div>
</body>
</html>