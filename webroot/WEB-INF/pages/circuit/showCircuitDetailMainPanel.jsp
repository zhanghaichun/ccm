<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<style>
.td-values-wrap {
	width:240px;
	word-wrap:break-word;
}
.ggcon {
	width:100%;
	box-sizing: border-box;
	background-color:#ededed;
}
.leftggcon {
	float:left;
	width:75%;
	border-right:1px solid #b4b4b4;
	box-sizing: border-box;
}
.rightggcon {
	float:right;
	width:25%;
	box-sizing: border-box;
}
.ggclean {
	clear:both;
}
.topggbg {
	background-color:#f7f7f7;
	border-bottom:1px solid #b4b4b4;
	height:32px;
	line-height:32px;
	text-align:center;
	font-weight:bold;
	font-size:11px;
	width:100%;
} 
.twolr {
	width:100%;
	box-sizing: border-box;
}
.leftggl {
box-sizing: border-box;
	width:30%;
	float:left;
	border-right:1px solid #b4b4b4;
}
.leftggr {
box-sizing: border-box;
	float:right;
	width:70%;
}
.toplggr {
	width:100%;
	border-bottom:1px solid #b4b4b4;
}
.ggl {
	float:left;
}
.ggr {
	float:right;
}
.ggn {
	width:130px;
}
.ggm {
	width:100px;
}
.tablegg{
	padding:0 5px;
	margin:0;
}
.leftggl ul li {
	list-style-type:none;
	margin:0;
	padding:0;
}
.Cir_Det_table1 td {
	padding: 1px 0;
}
.Cir_Det {
    width: 100%;

}
.topggl {
	width:100%;
/*height:360px;*/}
.topggl table {
	padding:0 5px;

}
.botggl {
	border-top:1px solid #b4b4b4;
}
.botggl table {
	padding:0 5px;

}
.ggadl {
	border-right:1px solid #b4b4b4;
	width:50%;
	box-sizing: border-box;
}
.titigg {
	border-bottom: 1px solid #e4e4e4;
	height: 20px;
	line-height: 20px;
	text-align: center;
	font-weight:bold;
	color: #595758;
}
.ggadr {
	width:50%;
	box-sizing: border-box;
}
.ggbgtop {
	border-bottom: 1px solid #b4b4b4;
	width:100%;
	box-sizing: border-box;
}
.ggbgbot {
	width:100%;
	box-sizing: border-box;
}
.th333 {
	border-bottom: 1px solid #e4e4e4;
	height: 18px;
	line-height: 18px;
	text-align: left;
	color: #595758;
}
.datass table{
padding:0 5px;
}
.tablegg .ggl{
width:40%;}
.tablegg .ggr{
text-align:right;
width:60%;
word-wrap:break-word;
}
.tablegg2{
	padding:0 5px;
	margin:0;
}
.tablegg2 .ggl{
width:30%;}
.tablegg2 .ggr{
text-align:right;
width:70%;
word-wrap:break-word;
}
.tablegg3{
	padding:0 5px;
	margin:0;
}
.tablegg3 .ggl{
width:75%;}
.tablegg3 .ggr{
text-align:right;
width:25%;
word-wrap:break-word;
}
.tablegg li,.tablegg2 li,.tablegg3 li,.tablegg4 li{
	padding:0;
	margin:0;
}
.tablegg4{
	padding:0 5px;
	margin:0;
}
.tablegg4 .ggl{
text-align:left;
width:50%;}
.tablegg4 .ggr{
text-align:left;
width:50%;
word-wrap:break-word;
}
</style>
<link rel="stylesheet" type="text/css"
	href="include/javascript/Calendar/styles/calendar.css">
<link rel="stylesheet" type="text/css"
	href="include/javascript/flexbox/css/jquery.flexbox.css">
<link>
<script type="text/javascript"
	src="include/javascript/yui2/tabview-min.js"></script>
<script type="text/javascript"
	src="include/javascript/Calendar/simplecalendar.js"
	language="javascript"></script>
<script type="text/javascript" src="include/javascript/ccm/quicklink.js"
	language="javascript"></script>
<SCRIPT type="text/javascript"
	src="include/javascript/ccm/common/simpleValidation.js"></SCRIPT>
<SCRIPT type="text/javascript"
	src="include/javascript/ccm/common/common.js"></SCRIPT>
<script type="text/javascript"
	src="include/javascript/ccm/viewCircuitDetail.js" language="javascript"></script>
<script src="include/javascript/saninco/downLoad.js" type="text/javascript"></script>
<s:hidden id="vendorCircuitId" name="vendorCircuit.id"></s:hidden>
<div id="pageContainer" class="tabForm"
	onMouseDown="if(g_Calendar && g_Calendar.hide)g_Calendar.hide();"
	style="padding-bottom: 25px;">
  <form id="downloadForm" action="download.action" method="post" target="__downloadIframe"
		style="display: none;">
    <input type="hidden" name="filePath" />
    <input type="hidden" name="fileName" />
  </form>
  <h3 style="padding-top: 0px; padding-bottom: 4px;"> Circuit Detail </h3>
  <div class="Cir_Det" id = "ShowHeightID">
    <!--start yes-->
    <div class="ggcon">
      <div class="leftggcon">
        <div class="topggbg">Circuit</div>
        <div class="twolr">
          <div class="leftggl" id = "showHeightIDV">
            <div class="topggl">
             <ul class="tablegg">
              <li>
              <div class="ggl">Circuit Number</div>
              <div class="ggr">${vendorCircuit.circuitNumber}</div>
               <div class="ggclean"></div>
              </li>
               <li>
              <div class="ggl">Circuit Type</div>
              <div class="ggr">${vendorCircuit.circuitType}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Service Type</div>
              <div class="ggr">${vendorCircuit.serviceType}</div>
               <div class="ggclean"></div>
              </li> <li>
              
              <div class="ggl">Speed</div>
              <div class="ggr">${vendorCircuit.speed}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Status</div>
              <div class="ggr">${vendorCircuit.status}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Circuit Description</div>
              <div class="ggr">${vendorCircuit.circuitDescription}</div>
               <div class="ggclean"></div>
              </li>
              <li>
              <div class="ggl">First invoice Date</div>
<%--              <div class="ggr">${lastInvoiceDate}</div>--%>
              <div class="ggr"><s:date format="yyyy-MM-dd"
										name="vendorCircuit.firstInvoiceDate" /></div>
                                         <div class="ggclean"></div>
               <div class="ggclean"></div>
              </li>
              <li>
              <div class="ggl">Latest Invoice Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
										name="vendorCircuit.latestInvoiceDate" /></div>
                                         <div class="ggclean"></div>
<%--              <div class="ggr">${firstInvoiceDate}</div>--%>
               <div class="ggclean"></div>
              </li>
              <li>
              <div class="ggl">Last Modified Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
										name="vendorCircuit.lastModifiedDate" /></div>
                                         <div class="ggclean"></div>
              </li>
              </ul>
              <div class="ggclean"></div>
            
            
            
            </div>
            <div class="botggl">
              <div class="titigg">Vendor</div>
               <ul class="tablegg">
              <li>
              <div class="ggl">Vendor Name</div>
              <div class="ggr">${vendorCircuit.vendor.vendorName}</div>
               <div class="ggclean"></div>
              </li>
               <li>
              <div class="ggl">Ban</div>
              <div class="ggr">${vendorCircuit.accountNumber}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Vendor PON</div>
              <div class="ggr">${vendorCircuit.vendorPon}</div><div class="ggclean"></div>
              </li> <li>
               
              <div class="ggl">ASR Number</div>
              <div class="ggr">${vendorCircuit.asrNumber}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Order Number</div>
              <div class="ggr">${vendorCircuit.orderNumber}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Source</div>
              <div class="ggr">${vendorCircuit.source}</div>
               <div class="ggclean"></div>
              </li>
             
              </ul>
              
            </div>
          </div>
          <div class="leftggr">
            <div class="toplggr" id = "showHeightID2">
              <div class="titigg">Address</div>
              <div>
                <div class="ggl ggadl">
                   <ul class="tablegg2">
              <li>
              <div class="ggl"><strong>A Address</strong></div>
              <div class="ggr"></div>
               <div class="ggclean"></div>
              </li>
               <li>
              <div class="ggl">Company</div>
              <div class="ggr">${vendorCircuit.ACompany}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Address</div>
              <div class="ggr">${vendorCircuit.AAddress}</div><div class="ggclean"></div>
              </li> <li>
               
              <div class="ggl">City</div>
              <div class="ggr">${vendorCircuit.ACity}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Postal Code</div>
              <div class="ggr">${vendorCircuit.APostalCode}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Province</div>
              <div class="ggr">${vendorCircuit.AProvince}</div>
               <div class="ggclean"></div>
              </li>
             <li>
              <div class="ggl">Country</div>
              <div class="ggr">${vendorCircuit.ACountry}</div>
               <div class="ggclean"></div>
              </li>
              </ul>
              
                </div>
                <div class="ggr ggadr">
                  <ul class="tablegg2">
              <li>
              <div class="ggl"><strong>Z Address</strong></div>
              <div class="ggr"></div>
               <div class="ggclean"></div>
              </li>
               <li>
              <div class="ggl">Company</div>
              <div class="ggr">${vendorCircuit.ZCompany}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Address</div>
              <div class="ggr">${vendorCircuit.ZAddress}</div><div class="ggclean"></div>
              </li> <li>
               
              <div class="ggl">City</div>
              <div class="ggr">${vendorCircuit.ZCity}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Postal Code</div>
              <div class="ggr">${vendorCircuit.ZPostalCode}</div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Province</div>
              <div class="ggr">${vendorCircuit.ZProvince}</div>
               <div class="ggclean"></div>
              </li>
             <li>
              <div class="ggl">Country</div>
              <div class="ggr">${vendorCircuit.ZCountry}</div>
               <div class="ggclean"></div>
              </li>
              </ul>
                </div>
                <div class="ggclean"></div>
              </div>
            </div>
            <div class="botlggr">
              <div class="titigg">Date</div>
              <div>
                <div class="ggl ggadl datass" style="min-height:177px;" id = "newHeight">
              <ul class="tablegg3">
              <li>
              <div class="ggl">Installation Requested Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.installationRequestedDate" /></div>
               <div class="ggclean"></div>
              </li>
               <li>
              <div class="ggl">Installation Promised Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.installationPromisedDate" /></div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Installation Completed Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.installationCompletedDate" /></div><div class="ggclean"></div>
              </li> <li>
               
              <div class="ggl">Contract Start Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.circuitActiveStartDate" /></div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Contract Expiry Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.contractExpireDate" /></div>
               <div class="ggclean"></div>
              </li> 
              </ul>
                </div>
               <div class="ggr ggadr datass" style="min-height:157px;" id="righth">
                   <ul class="tablegg3">
              <li>
              <div class="ggl">Disconnection Requested Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.disconnectionRequestedDate" /></div>
               <div class="ggclean"></div>
              </li>
               <li>
              <div class="ggl">Disconnection Promised Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.disconnectionPromisedDate" /></div>
               <div class="ggclean"></div>
              </li> <li>
              <div class="ggl">Disconnection Completed Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.disconnectionCompletedDate" /></div><div class="ggclean"></div>
              </li> <li>
               
              <div class="ggl">Circuit Disconnected Date</div>
              <div class="ggr"><s:date format="yyyy-MM-dd"
																name="vendorCircuit.circuitDisconnectedDate" /></div>
               <div class="ggclean"></div>
              </li> 
              </ul>
                </div>
                <div class="ggclean"></div>
              </div>
            </div>
          </div>
          <div class="ggclean"></div>
        </div>
      </div>
      <!--leftggcon-->
      <div class="rightggcon">
        <div class="topggbg">Logical Path</div>
       <div class="ggbgtop datass" style="min-height: 176px;">
             <ul class="tablegg4">
              <li>
              <div class="ggl titigg"><strong>Circuit Type</strong></div>
              <div class="ggr titigg"><strong>Circuit Number</strong></div>
               <div class="ggclean"></div>
              </li>
              
              <c:forEach items="${logicalPathList}" var="lp">
                <li>
              <div class="ggl">${lp.circuit_type_name}</div>
              <div class="ggr"><a href="javascript:window.location.href='showCircuitDetail.action?vendorCircuitId=${lp.logical_path_circuit_id}'">${lp.logical_path_circuit_number}</a></div>
               <div class="ggclean"></div>
              </li> 
                </c:forEach>
              
              </ul>
        </div>
        <div class="ggbgbot datass">
          <div class="titigg">Customer</div>
          <ul class="tablegg4">
              <li>
              <div class="ggl">Customer Name</div>
              <div class="ggr">${vendorCircuit.customerName}</div>
               <div class="ggclean"></div>
              </li>
              
               <li>
              <div class="ggl">Billing Account Number</div>
              <div class="ggr">${vendorCircuit.billingAccountNumber}</div>
               <div class="ggclean"></div>
              </li>
              
                <li>
              <div class="ggl">Service ID</div>
              <div class="ggr">${vendorCircuit.customerId}</div>
               <div class="ggclean"></div>
              </li>
              
              </ul>
        </div>
      </div>
      <!--rightggcon-->
      <div class="ggclean"></div>
    </div>
    <!--start no-->
  </div>
  <div style="height: 10px; overflow: hidden;"></div>
  <div id="demo" class="yui-navset yui-navset-top">
    <ul class="yui-nav">
      <li class="selected"> <a href="#tab_scoa" onclick="createSCOAPage();"><em>SCOA</em></a> </li>
      <li> <a href="#tab_cost" onclick="createCostPage();"><em>Cost History</em></a> </li>
      <li> <a href="#tab_tariff" onclick="createLineItemAndAttachedFile();"><em>Tariff & Contract</em></a> </li>
    </ul>
    <!-- 
		<div id="tabRow_s1">
			<ul class="yui-nav">
				<li>
					<a href="#tab_scoa" onclick="createSCOAPage();">
						<div class="left"></div>
						<div class="middle">
							SCOA
						</div>
						<div class="right"></div> 
					</a>
				</li>
				<li>
					<a href="#tab_cost" onclick="createCostPage();">
						<div class="left"></div>
						<div class="middle">
							Cost History
						</div>
						<div class="right"></div> 
					</a>
				</li>
				<li>
					<a href="#tab_tariff" onclick="createTariffPage();">
						<div class="left"></div>
						<div class="middle">
							Tariff & Contract
						</div>
						<div class="right"></div> 
					</a>
				</li>
			</ul>
		</div>
		 -->
    <div class="clear"></div>
    <div class="yui-content" style="padding: 10px; background: #f6f6f6;">
      <div id="tab_scoa" style="width: 100%">
        <table border="0" cellpadding="0" cellspacing="0"
					style="table-layout: fixed; width: 100%;">
          <tr>
            <td><div align="left" style="width: 100%; overflow-x: auto;">
                <div id="_SCOA_dataTable"></div>
              </div></td>
          </tr>
        </table>
        <table>
          <tr>
            <td><span id="_SCOA_dataTable_page"></span> </td>
            <td><input value="Download to CSV" type="button" onclick="downloadScoaCsv()"
								style="margin-top: 9px; cursor: pointer;">
            </td>
          </tr>
        </table>
      </div>
      <div id="tab_cost" style="width: 100%">
        <table border="0" cellpadding="0" cellspacing="0"
					style="table-layout: fixed; width: 100%;">
          <tr>
            <td><div align="left" style="width: 100%; overflow-x: auto;">
                <div id="_cost_dataTable"></div>
              </div></td>
          </tr>
        </table>
        <table>
          <tr>
            <td><span id="_cost_dataTable_page"></span> </td>
            <td><input value="Download to CSV" type="button" onclick="downloadCostCsv()"
								style="margin-top: 9px; cursor: pointer;">
            </td>
          </tr>
        </table>
      </div>
      <div id="tab_tariff" style="width: 100%">
       <h3>Line Item
      </h3>
        <table border="0" cellpadding="0" cellspacing="0"
					style="table-layout: fixed; width: 100%;">
          <tr>
            <td><div align="left" style="width: 100%; overflow-x: auto;">
                <div id="_Line_Item"></div>
              </div></td>
          </tr>
        </table>
         <table>
          <tr>
            <td><span id="_Line_Item_page" style="display:block;padding-top:8px;"></span></td>
          </tr>
        </table>
        
    <!--     <h3>Agreement & Contract
      </h3>
        <table border="0" cellpadding="0" cellspacing="0"
					style="table-layout: fixed; width: 100%;">
          <tr>
            <td><div align="left" style="width: 100%; overflow-x: auto;">
                <div id="_agreement_contract"></div>
              </div></td>
          </tr>
        </table>
         <table>
          <tr>
            <td><span id="_agreement_contract_page" style="display:block;padding-top:8px;"></span></td>
          </tr>
        </table> -->
        
      <h3>Tariff & Contract
      </h3>
        <table border="0" cellpadding="0" cellspacing="0"
					style="table-layout: fixed; width: 100%;">
          <tr>
            <td><div align="left" style="width: 100%; overflow-x: auto;">
                <div id="_tariff_dataTable"></div>
              </div></td>
          </tr>
        </table>
        <table>
          <tr>
            <td><span id="_tariff_dataTable_page" style="display:block;padding-top:8px;"></span> </td>
            <td><input value="Download to CSV" type="button" onclick="downloadTariffCsv()"
								style="margin-top: 9px; cursor: pointer;">
              <input value="Upload Tariff & Contract" type="button"
								onclick="popupUploadTariffWindow()"
								style="margin-top: 9px; cursor: pointer;">
            </td>
          </tr>
        </table>
        
      </div>
    </div>
  </div>
</div>

<style type="text/css">
.CD_P{
	font-size:11px;
	/*width:390px;*/
	margin:10px 10px;
}
.cp_p_top{
	line-height:27px;
	padding-left:10px;
	color:#4e4d4d;
}
.cp_p_down{
	padding:10px 0 20px;
	line-height:23px;
	color:#4e4d4d;
}

.cp_p_down .lable{
	padding-top:1px;
}
.cp_p_down .lable .left{
	width:80px;

	padding-left:20px;
	float:left;
}

.cp_p_down .lable .right{
	float:left;
	padding-left:10px;
}

</style>
<div class="yui-pe-content" id="uploadTariffWindow"
	style="display: none;">
  <div class="hd">
    <h2> Attach Tariff & Contract </h2>
  </div>
  <div class="bd">
    <div class="CD_P">
      <div class="cp_p_down" style="position:relative;">
        <form id="uploadForm" action="doUploadAttachFile.action"
			enctype="multipart/form-data" method="POST" target="uploadFrame_0">
          <s:hidden name="vendorCircuitId"></s:hidden>
          <table width="100%" align="center" style="margin: auto;">
            <tr>
              <td> Attach File: </td>
              <td><div id="addFile" style="overflow-y:auto;height:100px;position:relative;margin-bottom:10px;">
                  <div id ="ContractFile1" style="padding:3px 0 0 0;height: 19px;" >
                    <div style="float:left;">
                      <input style="height:19px;width:215px;" type='text' id="_upload_text_attachment_01" disabled="disabled" class="upload_text">
                    </div>
                    <div style="float:left; margin:0 0 0 2px"> <span class="ccm_upload_img">
                      <input onchange="document.getElementById('_upload_text_attachment_01').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
                      <input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
                      </span> </div>
                    <div class="approveicon" onclick="deletefile('ContractFile1');"></div>
                    <div class="clear" ></div>
                  </div>
                  <div id ="ContractFile2" style="padding:3px 0 0 0;height: 19px;" >
                    <div style="float:left;">
                      <input style="height:19px;width:215px;" type='text' id="_upload_text_attachment_02" disabled="disabled" class="upload_text">
                    </div>
                    <div style="float:left; margin:0 0 0 2px"> <span class="ccm_upload_img">
                      <input onchange="document.getElementById('_upload_text_attachment_02').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
                      <input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
                      </span> </div>
                    <div class="approveicon" onclick="deletefile('ContractFile2');"></div>
                    <div class="clear" ></div>
                  </div>
                  <div id ="ContractFile3" style="padding:3px 0 0 0;height: 19px;" >
                    <div style="float:left;">
                      <input style="height:19px;width:215px;" type='text' id="_upload_text_attachment_03" disabled="disabled" class="upload_text">
                    </div>
                    <div style="float:left; margin:0 0 0 2px"> <span class="ccm_upload_img">
                      <input onchange="document.getElementById('_upload_text_attachment_03').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
                      <input style="height:19px;width:65px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
                      </span> </div>
                    <div class="approveicon" onclick="deletefile('ContractFile3');"></div>
                    <div class="clear" ></div>
                  </div>
                </div></td>
            <tr>
              <td> Notes: </td>
              <td><textarea id="uploadNotes" name="uploadNotes" cols="" rows=""
							style="width: 300px; height: 50px;max-width:300px;"></textarea>
              </td>
            </tr>
            <tr>
              <td>&nbsp;</td>
              <td><div class="lable"
					    style="padding-top: 10px; text-align: right; padding-right: 38px;">
                  <input style="height: 20px;text-align:center; padding:0 5px;" type="button" value="Add" onclick="addUploadForm()"/>
                  <input style="height: 20px;text-align:center; padding:0 5px;" type="button" value="Submit" onclick="submitUploadForm()"/>
                  <input style="height: 20px;text-align:center; padding:0 5px;" type="button" value="Cancel" onclick="cancelUploadForm()"/>
                </div></td>
            </tr>
            </tr>
            
          </table>
          <!--
				<div class="lable">
					<Div class="left">
						Attach File:
					</Div>
					<div class="right">
						<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_01" disabled="disabled" class="upload_text">
       					<span class="ccm_upload_img">
        					<input onchange="document.getElementById('_upload_text_attachment_01').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
       					</span>
					</div>
					<div class="clear"></div>
				</div>
				<div class="lable">
					<Div class="left">
						Attach File:
					</Div>
					<div class="right">
						<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_02" disabled="disabled" class="upload_text">
       					<span class="ccm_upload_img">
        					<input onchange="document.getElementById('_upload_text_attachment_02').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
       					</span>
					</div>
					<div class="clear"></div>
				</div>
				<div class="lable">
					<Div class="left">
						Attach File:
					</Div>
					<div class="right">
						<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_03" disabled="disabled" class="upload_text">
       					<span class="ccm_upload_img">
        					<input onchange="document.getElementById('_upload_text_attachment_03').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
       					</span>
					</div>
					<div class="clear"></div>
				</div>
				<div class="lable">
					<Div class="left">
						Attach File:
					</Div>
					<div class="right">
						<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_04" disabled="disabled" class="upload_text">
       					<span class="ccm_upload_img">
        					<input onchange="document.getElementById('_upload_text_attachment_04').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
       					</span>
					</div>
					<div class="clear"></div>
				</div>
				<div class="lable">
					<Div class="left">
						Attach File:
					</Div>
					<div class="right">
						<input style="height:19px;width:195px;" type='text' id="_upload_text_attachment_05" disabled="disabled" class="upload_text">
       					<span class="ccm_upload_img">
        					<input onchange="document.getElementById('_upload_text_attachment_05').value=this.value;" class="ccm_file" type="file" name="uploads" size="1" />
        					<input style="height:19px;position:absolute;right:0px;top:0px;" type="button" value="Browse..."/>
       					</span>
					</div>
					<div class="clear"></div>
				</div>
				<div class="lable">
					<Div class="left">
						Notes:
					</Div>
					<div class="right">
						<textarea id="uploadNotes" name="uploadNotes" cols="" rows=""
							style="width: 268px; height: 50px;"></textarea>
					</div>
					<div class="clear"></div>
				</div>
				<div class="lable"
					style="padding-top: 10px; text-align: right; padding-right: 38px;">
					<input style="height: 20px;" type="button" value="Submit" onclick="submitUploadForm()"/>
					&nbsp;
					<input style="height: 20px;" type="button" value="Cancel" onclick="cancelUploadForm()"/>
				</div>
			-->
        </form>
      </div>
    </div>
  </div>
</div>
<div class="update-ReportAdminTags" style=" border:1px #666666 solid;" id="Tariff_or_Contract_Detail">
<div id="Tariff_or_Contract_DetailPanel"/>
</div>
<iframe name="uploadFrame_0" id="uploadFrame_0" style="display: none"  src="javascript:false"></iframe>
<iframe id="__downloadIframe" style="display: none;"></iframe>
