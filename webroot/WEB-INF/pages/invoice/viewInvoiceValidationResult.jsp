
<html>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
 <head>
 	<meta charset="utf-8">
    <link rel="stylesheet" href="include/css/viewInvoiceValidationResult.css">
    <link rel="stylesheet" type="text/css" href="include/css/ccm.css" />
    
    <!-- tooltip 提示。 -->
    <link rel="stylesheet" type="text/css" href="include/javascript/flexbox/css/jquery.flexbox.css">
    <link rel="stylesheet" href="include/css/hint.min.css">
	
	<script type="text/javascript" src="include/javascript/flexbox/js/jquery.flexbox.js"></script>
	<script type="text/javascript" src="include/javascript/jquery/jquery.ui.core.js"></script>
	<script type="text/javascript" src="include/javascript/jquery/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="include/javascript/jquery/jquery.ui.accordion.js"></script>
    <script type="text/javascript">
    
    
    function cssTrimming(){ 
      if($(".memory-container").css('display') == 'none'){
         setTimeout('changCssHeight()',50); 
      }    
    }; 
    function changCssHeight(){ 
      var height = $(".filters-exhibition").height();
      $(".right-filters").attr("style","height:"+height+"px;");
      $(".left-filters").attr("style","height:"+height+"px;");
     };


     
    $(function(){
        $('#VL_vendorList__scoa_codeing').html("");
		SCOACodeing = $('#VL_vendorList__scoa_codeing').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 260,
			name : "svo.accountCodeId"
		});
		SCOACodeing.setValue("");
	});
	
	$(function(){
     	$('#VL_vendorList__scoa_code').html("");
		scoa_code = $('#VL_vendorList__scoa_code').flexbox(SC_Array, {
			maxCacheBytes : 200000,
			highlightMatches : true,
			autoCompleteFirstMatch : false,
			paging: false,
			width : 250,
			name : "svo.accountCodeId"
		});
		scoa_code.setValue("");
	});

    

    	// 正常情况下这里的数据是从后台中返回的
    	/**
    	 * 
         *	使用jsp中的forEach 标签进行数据的循环写入。

         *	※※※现在使用的是硬性写死的假数据。
    	 */

    	// Product Filter.
    	var product_array = {
    	    "results": [
    	        {
    	            "id": "all",
    	            "name": ""
    	        }
    	        
    	     <c:forEach items="${productList}" var="v">
		       ,{
		            "id": "${v.key}",
		            "name": "${v.value}"
		        }
	         </c:forEach>

    	        
    	    ],
    	    "total": ${fn:length(productList)}
    	};

    	
    	$(function(){

    		// js中通过 search_filters_productId.getValue() 取值
    	    search_filters_productId = $('#product-filter-list').flexbox(product_array, {
    	        maxCacheBytes : 200000,
    	        highlightMatches : true,
    	        autoCompleteFirstMatch : false,
    	        paging: false,
    	        width : 170,
    	        onSelect : function(){
    	        }
    	    });
    	    search_filters_productId.setValue("${memory.product_id}");
    	    $("#product-filter-list_ctr").attr("style","width:170px;top: 18px; left: 0px; margin-left: 0px; display: none;");
    	});

    	// Product Component Filter.
    	var productComponent_array = {
    	    "results": [
    	        {
    	            "id": "all",
    	            "name": ""
    	        }
    	        
    	     <c:forEach items="${productComponentList}" var="v">
		       ,{
		            "id": "${v.key}",
		            "name": "${v.value}"
		        }
	         </c:forEach>
    	        
    	    ],
    	    "total": ${fn:length(productComponentList)}
    	};

    	$(function(){
    		// js中通过 search_filters_productComponentId.getValue() 取值
    	    search_filters_productComponentId = $('#product-component-filter-list').flexbox(productComponent_array, {
    	        maxCacheBytes : 200000,
    	        highlightMatches : true,
    	        autoCompleteFirstMatch : false,
    	        paging: false,
    	        width : 170,
    	        onSelect: function(){
    	        }
    	    });
    	    search_filters_productComponentId.setValue("${memory.product_component_id}");
    	     $("#product-component-filter-list_ctr").attr("style","width:170px;top: 18px; left: 0px; margin-left: 0px; display: none;");
    	});

    	// Province Filter.
    	var province_array = {
    	    "results": [
    	        {
    	            "id": "all",
    	            "name": ""
    	        }
    	        
    	     <c:forEach items="${provinceList}" var="v">
		       ,{
		            "id": "${v.key}",
		            "name": "${v.value}"
		        }
	         </c:forEach>
    	        
    	    ],
    	    "total": ${fn:length(provinceList)}
    	};

    	$(function(){
    		// js中通过 search_filters_provinceId.getValue() 取值
    	    search_filters_provinceId = $('#province-filter-list').flexbox(province_array, {
    	        maxCacheBytes : 200000,
    	        highlightMatches : true,
    	        autoCompleteFirstMatch : false,
    	        paging: false,
    	        width : 170,
    	        onSelect: function(){
    	        }
    	    });
    	    search_filters_provinceId.setValue("${memory.province_id}");
    	    $("#province-filter-list_ctr").attr("style","width:170px;top: 18px; left: 0px; margin-left: 0px; display: none;");
    	});
    	
    	
    </script>
 </head>
<body>

	<div id="pageContainer" class="tabForm">
		<div class="header-line clearfix">
	    	<h2 class="modal-title">Validation Result</h2>
<%--			<c:if test="${itemType == 3}">--%>
<%--				<label class="memory-button-label" for="memory-container-checkbox" onclick = "cssTrimming();">--%>
<%--					<img class="modal-memory-button" src="include/images/validation_status/set_up.png" alt="">--%>
<%--					<span class="word-flag">Memory</span>--%>
<%--				</label>--%>
<%--			</c:if>--%>
			<input type="checkbox" id="memory-container-checkbox">
			<div class="memory-container">
				<h2 class="memory-title">Memory</h2>
				<!-- 显示当前的搜索条件 -->
				<div class="filters-exhibition clearfix">
					<div class="left-filters" id = "divLeft">
						<table>
							<tr>
								<td>Account Number</td>
								<td class="account-number-value" id = "_accountNumber">${memory.account_number}</td>
							</tr>
							<tr>
								<td>Charge Type</td>
								<td class="charge-type-value" id = "_chargeType">${memory.charge_type}</td>
							</tr>
							<tr>
								<td>Circuit Number</td>
								<td class="circuit-number-value" id = "_circuitNumber">${memory.circuit_number}</td>
							</tr>
							<tr>
								<td>USOC</td>
								<td class="usoc-value" id = "_USOC">${memory.usoc}</td>
							</tr>
						</table>
					</div>
					<div class="right-filters" id ="divRight">
						<table>
							<tr>
								<td>Item Name</td>
								<td class="item-name-value" id = "_itemName">${memory.item_name}</td>
							</tr>
							<tr>
								<td>Line Item Code</td>
								<td class="line-item-code-value" id = "_lineItemCode">${memory.line_item_code}</td>
							</tr>
							<tr>
								<td>Description</td>
								<td class="description-value" id = "_description">${memory.description}</td>
							</tr>
							<!-- 这一行只是为了撑开样式的，不用做处理 -->
							<tr>
								<td></td>
								<td></td>
							</tr>
						</table>
					</div>
				</div>

				<!-- 用户输入搜索条件的控件 -->
				<div class="filters-search-controller">
					<s:form id="form0" action="">
						<table class="search-filters">
							<tr>
								<td>Rate</td>
								<td style="width: 32%;">
									<input type="text" value="${memory.rate}" id = "_rate" cssClass="rate validate-number">
									
								</td>
								<td class="product-filter-td">Product</td>
								<td>
									<div id="product-filter-list"></div>
								</td>
							</tr>
							<tr>
								<td>Product Component</td>
								<td>
									<div id="product-component-filter-list"></div>
								</td>
								<td class="province-filter-td">Province</td>
								<td>
									<div id="province-filter-list"></div>
								</td>
							</tr>
						</table>
					</s:form>
				</div>

				<!-- memory 操作按钮。 -->
				<div class="memory-buttons-container">
					<input class="confirm-button" type="button" value="Confirm" onclick="searchMemory()">
					<input class="cancel-button" type="button" value="Cancel" onclick="closeMemoryModal()">
					<input class="clear-button" type="button" value="clear" onclick="clearFilterConditions()">
					<input type="hidden" value="${memory.type}" id = "_confirmType"/>
					<input type="hidden" value="${memory.ban_id}" id = "_banId"/>
					<input type="hidden" value="${memory.memory_id}" id = "_memoryId"/>
					
				</div>
			</div>
		</div>

		<c:forEach  items="${validationResultList}" var="validationResultList">
		   <div class="table-container">
		   		<br>
			   <table width="100%" cellspacing="0" cellpadding="0" border="0"
		    style="border-top: 0px none; margin-bottom: 4px" class="validation-result-table">
	 				<!-- Validation status and quantity row. -->
	 				<tr>
						<td colspan="2">
							<div class="validation-method">
								<c:out value="${validationResultList.audit_source_name}"></c:out>
								<c:if test="${not empty validationResultList.audit_source_description}">
									<a href="#${validationResultList.audit_source_name}">
									  <img src="include/images/validation_status/help.png" alt="">
									</a>
								</c:if>
								
							</div>
							
							
							<div id="${validationResultList.audit_source_name}" class="desc-popup">
							  <div class="desc-popup-container">
							      <div class="title-bar">Validation Method Description</div>
							      <div class="content-container">
									  <a class="help-modal-close"></a>
									  <div class="help-content"><c:out value="${validationResultList.audit_source_description}"></c:out></div>
								  </div>
							  </div>
							</div>
						</td>
					</tr>
					<tr>
						<td class="validation-status">
						    <label>Status:</label> 
						    <span class="validation_status"><c:out value="${validationResultList.audit_status_name}"></c:out></span>

		                 
	                    	<!-- Passed. -->
	                    	<c:if test="${validationResultList.audit_status_id == 1}">
	                    		<span class="icon pass"></span>
	                    	</c:if>
	                    	<!-- Failed. -->
	                    	<c:if test="${validationResultList.audit_status_id == 2}">
	                    		<span class="icon failed"></span>
	                    	</c:if>
	                    	<!-- Can not validate. -->
	                    	<c:if test="${validationResultList.audit_status_id == 3}">
	                    		 <span class="icon can-not-validate"></span>
	                    	</c:if>
	                    	<!-- No reference. -->
	                    	<c:if test="${validationResultList.audit_status_id == 4}">
	                    		<span class="icon no-reference"></span>
	                    	</c:if>
		                   
						</td>
						
						<c:if test="${validationResultList.audit_source_id != 10003 && validationResultList.audit_source_id != 10002}">
							<c:if test="${not empty  validationResultList.quantity}">
								<td>
									<label>Quantity: </label>
									<c:out value="${validationResultList.quantity}"></c:out>
								</td> 
							</c:if>     
						</c:if>
					</tr>
					<!-- Actual amount and expect amount row -->
					<tr> 
	                    
						<c:if test="${not empty validationResultList.actual_amount}">
							<c:choose>
								<c:when test="${validationResultList.audit_source_id == 8002 || validationResultList.audit_source_id == 8005 || validationResultList.audit_source_id == 8008}">
									<td>
										<label>Actual Minutes:</label>
										<span class="actual_amount amount"><c:out value="${validationResultList.actual_amount}"></c:out></span>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 8010}">
									<td>
										<label>Actual Amount:</label>
										<span class="actual_amount amount">$<c:out value="${validationResultList.actual_amount}"></c:out></span>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 10001}">
									<td>
										<label>Actual Trunk:</label>
										<span class="actual_amount amount"><c:out value="${validationResultList.actual_amount}"></c:out></span>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 10002}">
									<td>
										<label>Actual Imbalance:</label>
										<span class="actual_amount amount">><c:out value="${validationResultList.actual_amount}"></c:out>%</span>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 10003}">
									<td>
										<label>Actual Rate:</label>
										<span class="actual_amount amount">$<c:out value="${validationResultList.actual_amount}"></c:out></span>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 12002}">
									<td>
										<label>Pass:</label>
										<span class="actual_amount amount"><c:out value="${validationResultList.actual_amount}"></c:out></span>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 8006 || validationResultList.audit_source_id == 8009}">
									<td>
										<label>Actual Counts:</label>
										<span class="actual_amount amount"><c:out value="${validationResultList.actual_amount}"></c:out></span>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<label>Actual Amount:</label>
										<span class="actual_amount amount">$<c:out value="${validationResultList.actual_amount}"></c:out></span>
									</td>
								</c:otherwise>						
							</c:choose> 
						</c:if>
					       
						<c:if test="${not empty validationResultList.expect_amount}">
							<c:choose>
								<c:when test="${validationResultList.audit_source_id == 8002 || validationResultList.audit_source_id == 8005 || validationResultList.audit_source_id == 8008}">
									<td>
										<label>Expect Minutes:</label>
										<c:choose>
											<c:when test="${validationResultList.audit_status_id == 2}">
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:when>
											<c:otherwise>
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:otherwise>
										</c:choose>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 8010}">
									<td>
										<label>Expect Amount:</label>
										<c:choose>
											<c:when test="${validationResultList.audit_status_id == 2}">
												<span class="expect_amount amount">$<c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:when>
											<c:otherwise>
												<span class="expect_amount amount">$<c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:otherwise>
										</c:choose>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 10001}">
									<td>
										<label>Expect Trunk:</label>
										<c:choose>
											<c:when test="${validationResultList.audit_status_id == 2}">
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:when>
											<c:otherwise>
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:otherwise>
										</c:choose>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 10002}">
									<td>
										<label>Expect Imbalance:</label>
										<c:choose>
											<c:when test="${validationResultList.audit_status_id == 2}">
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out>%</span>
											</c:when>
											<c:otherwise>
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out>%</span>
											</c:otherwise>
										</c:choose>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 10003}">
									<td>
										<label>Expect Rate:</label>
										<c:choose>
											<c:when test="${validationResultList.audit_status_id == 2}">
												<span class="expect_amount amount">$<c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:when>
											<c:otherwise>
												<span class="expect_amount amount">$<c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:otherwise>
										</c:choose>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 12002}">
									<td>
										<label>Failed:</label>
										<c:choose>
											<c:when test="${validationResultList.audit_status_id == 2}">
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:when>
											<c:otherwise>
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:otherwise>
										</c:choose>
									</td>
								</c:when>
								<c:when test="${validationResultList.audit_source_id == 8006 || validationResultList.audit_source_id == 8009}">
									<td>
										<label>Expect Counts:</label>
										<c:choose>
											<c:when test="${validationResultList.audit_status_id == 2}">
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:when>
											<c:otherwise>
												<span class="expect_amount amount"><c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:otherwise>
										</c:choose>
									</td>
								</c:when>
								<c:otherwise>
									<td>
										<label>Expect Amount:</label>
										<c:choose>
											<c:when test="${validationResultList.audit_status_id == 2}">
												<span class="expect_amount amount">$<c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:when>
											<c:otherwise>
												<span class="expect_amount amount">$<c:out value="${validationResultList.expect_amount}"></c:out></span>
											</c:otherwise>
										</c:choose>
									</td>
								</c:otherwise>
							</c:choose>
						</c:if>
					
					</tr>
					<!-- Product and component row. -->
					<tr>
						<c:if test="${not empty validationResultList.product_name}">
							<td>
								<label>Product: </label>
								<c:out value="${validationResultList.product_name}"></c:out>
							</td>
						</c:if>
						<c:if  test="${not empty validationResultList.component_name}">
							<td>
								<label class="component-label">Component: </label>
								<div class="component">
									<c:out value="${validationResultList.component_name}"></c:out>
								</div>
							</td>
						</c:if>
					</tr>
					<tr>
						<td colspan="2">
							<label>Validation Reference:</label>
							<c:out value="${validationResultList.audit_reference_type_name}"></c:out>
						</td>
					</tr>
					<tr>
						<c:if test="${ not empty validationResultList.notes_data}">
							<td colspan="2">
								<label class="notes-label">Notes:</label>
								<div class="audit-result-notes">
									<span>${validationResultList.notes_data}</span>
								</div>
							</td>
						</c:if>
						<c:if test="${empty validationResultList.notes_data}">
							<c:if test="${ not empty validationResultList.notes_notes}">
							    <td colspan="2">
								   <label class="notes-label">Notes:</label>
								   <div class="audit-result-notes-notes">
									<span>${validationResultList.notes_notes}</span>
								   </div>
							    </td>
						   </c:if>
						</c:if>
					</tr>
					
					<tr>
						<c:if test="${validationResultList.audit_source_id == 12002}">
						    <td colspan="2">
							   <input type="button" value="Download Telephone Validation" width="135" onclick="downloadTelephoneNumberValidationExcel()">
						    </td>
					   </c:if>
					</tr>
					<tr>
					    <!-- 只有在reference type 是 Contract, Tariff, Price List 的 时候才会显示这个字段。  -->
						<c:if test="${validationResultList.audit_reference_type_id == 2 || validationResultList.audit_reference_type_id == 3 || validationResultList.audit_reference_type_id== 5}">
							<td colspan="2" class="reference_type_info">
							   <label><c:out value="${validationResultList.audit_reference_type_name}"></c:out>:</label>
							    <img src="include/images/contract_tariff_price_list_detail_icon.png" style="margin-top:6px;" onclick="showValidationDetail(${validationResultList.audit_reference_type_id})">
							    <a href="javascript:void(0)" class="hint--bottom-right" onclick="showValidationDetail(${validationResultList.audit_reference_type_id})">
							    <c:out value="${validationResultList.reference_type_name}"></c:out>
							    </a>
								<!-- --------------------------------------------Tariff-------------------------------------------- -->
								<div id="show_tariff_validation_detail" class="ctp_validation_detail">
									<table class="ctp_validation_detail_table_top">
									    <tr>
									        <td style="width:15%; color: #444444; height:35px"> File Name </td>
									        <td style="color: black; font-weight: 900;"> ${validationResultList.ctp_data.file_name}</td>
									    </tr>
									</table>
									<div class="link-container">
										<table class="ctp_tariff_validation_detail_table_left">
                                            <c:if test="${ not empty validationResultList.ctp_data.page }">
                                                <tr>
                                                    <td class="detail_left_td"> Page </td>
                                                    <td class="detail_right_td">${validationResultList.ctp_data.page}</td>
                                                </tr>
                                            </c:if>    
										    

                                            <c:if test="${ not empty validationResultList.ctp_data.part_section }">
                                                <tr>
                                                    <td class="detail_left_td"> Part Section </td>
                                                    <td class="detail_right_td">${validationResultList.ctp_data.part_section}</td>
                                                </tr>
                                            </c:if>
										    

                                            <c:if test="${ not empty validationResultList.ctp_data.item_number }">
                                                <tr>
                                                    <td class="detail_left_td"> Item# </td>
                                                    <td class="detail_right_td">${validationResultList.ctp_data.item_number}</td>
                                                </tr>
                                            </c:if>
										    

                                            <c:if test="${ not empty validationResultList.ctp_data.paragraph }">
                                                <tr>
                                                    <td class="detail_left_td"> Paragraph </td>
                                                    <td class="detail_right_td">${validationResultList.ctp_data.paragraph}</td>
                                                </tr>
                                            </c:if>
										    

                                            <c:if test="${ not empty validationResultList.ctp_data.tariff_last_revised }">
                                                <tr>
                                                    <td class="detail_left_td"> Tariff Last Revised </td>
                                                    <td class="detail_right_td">${validationResultList.ctp_data.tariff_last_revised}</td>
                                                </tr>
                                            </c:if>
										    

                                            <c:if test="${ not empty validationResultList.ctp_data.service_type }">
                                                <tr>
                                                    <td class="detail_left_td"> Server Type</td>
                                                    <td class="detail_right_td">${validationResultList.ctp_data.service_type}</td>
                                                </tr>
                                            </c:if>
										    

                                            <c:if test="${ not empty validationResultList.ctp_data.band }">
                                                <tr>
                                                    <td class="detail_left_td"> Ban</td>
                                                    <td class="detail_right_td">${validationResultList.ctp_data.band}</td>
                                                </tr>
                                            </c:if>
										    

                                            <c:if test="${ not empty validationResultList.ctp_data.provider }">
                                                <tr>
                                                    <td class="detail_left_td"> Province</td>
                                                    <td class="detail_right_td">${validationResultList.ctp_data.provider}</td>
                                                </tr>
                                            </c:if>
										    
										</table>

                                        <div class="table-separate-line"></div>

										<table class="ctp_tariff_validation_detail_table_right">
                                            <tr>
                                                <td class="rate_text_td">
                                                    <div class="rate-text-label">Rate Text:</div>
                                                    <div class="rate-text">${validationResultList.ctp_data.rate_reference_info}</div>
                                                </td>
                                            </tr>
										</table>
									</div>
									<div>
										<c:if test="${ not empty validationResultList.attachment_point_id}">
											<img src="include/images/download1.gif" style="margin-top:6px;" onclick="SANINCO.Download(${validationResultList.attachment_point_id})">
										</c:if>
									</div>
									<a class="ctp_a" onclick="showValidationDetail(${validationResultList.audit_reference_type_id})"></a>
								</div>
								
								<!-- --------------------------------------------Contract-------------------------------------------- -->
								<div id="show_contract_validation_detail" class="ctp_validation_detail">
									<table class="ctp_validation_detail_table_top">
									    <tr>
									        <td style="width:15%; color: #444444; height:35px"> File Name </td>
									        <td style="color: black; font-weight: 900;"> ${validationResultList.ctp_data.file_name}</td>
									    </tr>
									</table>
									
									<div class="link-container">
										<table class="ctp_contract_validation_detail_table contract-link-left-part">
										    <c:if test="${ not empty validationResultList.ctp_data.expiry_date}">
                                                <tr>
                                                    <td class="detail_left_td"> Term Expire Date </td>
                                                    <td class="detail_right_td"> ${validationResultList.ctp_data.expiry_date}</td>
                                                </tr>
                                            </c:if>
                                            
                                            <c:if test="${not empty validationResultList.ctp_data.service_type}">
                                                <tr>
                                                    <td class="detail_left_td"> Service Type </td>
                                                    <td class="detail_right_td"> ${validationResultList.ctp_data.service_type}</td>
                                                </tr>
                                            </c:if>
                                            
                                            <c:if test="${not empty validationResultList.ctp_data.usoc_description}">
                                                <tr>
                                                    <td class="detail_left_td"> USOC Description </td>
                                                    <td class="detail_right_td"> ${validationResultList.ctp_data.usoc_description}</td>
                                                </tr>
                                            </c:if>
                                            
                                            <c:if test="${not empty validationResultList.ctp_data.circuit_number}">
                                                <tr>
                                                    <td class="detail_left_td"> Circuit Number </td>
                                                    <td class="detail_right_td"> ${validationResultList.ctp_data.circuit_number}</td>
                                                </tr>
                                            </c:if>
                                            
                                            <c:if test="${not empty validationResultList.ctp_data.amendment}">
                                                <tr>
                                                    <td class="detail_left_td"> Amendment </td>
                                                    <td class="detail_right_td"> ${validationResultList.ctp_data.amendment}</td>
                                                </tr>
                                            </c:if>
                                            
                                            <c:if test="${not empty validationResultList.ctp_data.schedule}">
                                                <tr>
                                                    <td class="detail_left_td"> Schedule</td>
                                                    <td class="detail_right_td"> ${validationResultList.ctp_data.schedule}</td>
                                                </tr>
                                            </c:if>
										</table>

                                        <div class="table-separate-line"></div>

                                        <table class="contract-link-right-part">
                                            <tr>
                                                <td class="rate_text_td">
                                                    <div class="rate-text-label">Rate Text</div>
                                                    <div class="rate-text">${validationResultList.ctp_data.rate_reference_info}</div> 
                                                </td>
                                            </tr>
                                        </table>
									</div>
									<div>
										<c:if test="${ not empty validationResultList.attachment_point_id}">
											<img src="include/images/download1.gif" style="margin-top:6px;" onclick="SANINCO.Download(${validationResultList.attachment_point_id})">
										</c:if>
									</div>
									<a class="ctp_a" onclick="showValidationDetail(${validationResultList.audit_reference_type_id})"></a>
								</div>
								
								<!-- --------------------------------------------Price List-------------------------------------------- -->
								<div id="show_price_list_validation_detail" class="ctp_validation_detail">
									<table class="ctp_validation_detail_table_top">
									    <tr>
									        <td style="width:15%; color: #444444; height:35px"> File Name </td>
									        <td style="color: black; font-weight: 900;"> ${validationResultList.ctp_data.file_name}</td>
									    </tr>
									</table>
									<div style="width: 100%; overflow: auto; border-bottom-color: #cccccc;
														border-bottom-style: inherit;
														border-bottom-width: 1">
										<table class="ctp_price_list_validation_detail_table_left">
										    <tr>
										        <td class="detail_left_td"> Service Type </td>
										        <td class="detail_right_td">${validationResultList.ctp_data.service_type}</td>
										    </tr>
										    <tr>
										        <td class="detail_left_td"> Ban </td>
										        <td class="detail_right_td">${validationResultList.ctp_data.band}</td>
										    </tr>
										    <tr>
										        <td class="detail_left_td"> Effective Date </td>
										        <td class="detail_right_td">${validationResultList.ctp_data.effective_date}</td>
										    </tr>
										</table>
										<table class="ctp_price_list_validation_detail_table_right">
										    <tr>
										        <td class="detail_left_td"> Rate Mode </td>
										        <td class="detail_right_td">${validationResultList.ctp_data.rate_mode}</td>
										    </tr>
										    <tr>
										        <td class="detail_left_td"> Rate </td>
										        <td class="detail_right_td" id="pricelistValidationRate">${validationResultList.ctp_data.rate}</td>
										    </tr>
                                            <tr>
                                                <td class="detail_left_td"> Rate Effective Date </td>
                                                <td class="detail_right_td">${validationResultList.ctp_data.rate_effective_date}</td>
                                            </tr>
										</table>
									</div>
									<div>
										<c:if test="${ not empty validationResultList.attachment_point_id}">
											<img src="include/images/download1.gif" style="margin-top:6px;" onclick="SANINCO.Download(${validationResultList.attachment_point_id})">
										</c:if>
									</div>
									<a class="ctp_a" onclick="showValidationDetail(${validationResultList.audit_reference_type_id})"></a>
								</div>
							</td>
						</c:if>
					</tr>

				</table>
	            <input type="hidden" class="reference_type_id" value="${validationResultList.reference_type_id}">
	            <input type="hidden" class="reference_type_file_id" value="${validationResultList.reference_type_file_id}"> 
	            <input type="hidden" class="reference_type_name" value="${validationResultList.reference_type_name}">  
			</div>
		</c:forEach>  

	</div>
    <script type="text/javascript">
    </script>
 
  
</body>
</html>