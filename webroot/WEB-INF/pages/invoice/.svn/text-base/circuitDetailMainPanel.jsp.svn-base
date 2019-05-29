<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!--<script>
var allh = window.document.getElementById('allheight');
var toph = window.document.getElementById('topheight');
var righth = window.document.getElementById('righth');
var lefth = window.document.getElementById('lefth');
lefth.style.height = allh-toph + 'px';
righth.style.height = allh-toph + 'px';
</script>-->
<style>

.td-values-wrap {
	width:240px;
	word-wrap:break-word;
}
.ggcon {
	width:100%;
	background-color:#ededed;
}
.leftggcon {
	float:left;
	width:815px;
	border-right:1px solid #b4b4b4;
}
.rightggcon {
	float:right;
	width:260px;
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
}
.leftggl {
	width:250px;
	float:left;
	border-right:1px solid #b4b4b4;
}
.leftggr {
	float:right;
	width:564px;
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
	width:282px;
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
	width:281px;
}
.ggbgtop {
	border-bottom: 1px solid #b4b4b4;
	width:260px;
}
.ggbgbot {
	width:260px;
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
width:110px;}
.tablegg .ggr{
text-align:right;
width:130px;
word-wrap:break-word;
}
.tablegg2{
	padding:0 5px;
	margin:0;
}
.tablegg2 .ggl{
width:90px;}
.tablegg2 .ggr{
text-align:right;
width:182px;
word-wrap:break-word;
}
.tablegg3{
	padding:0 5px;
	margin:0;
}
.tablegg3 .ggl{
width:205px;}
.tablegg3 .ggr{
text-align:right;
width:66px;
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
width:132px;}
.tablegg4 .ggr{
text-align:left;
width:118px;
word-wrap:break-word;
}
</style>
<div class="update-ReportAdminTags" style="width:1100px; border-top:1px #666666 solid;border-bottom:1px #666666 solid;" id="circuitDetail">
  <div class="tabForm">
    <div class="Cir_Det" id = "heightID">
      <!--start yes-->
      <div class="ggcon" id="allheight">
        <div class="leftggcon">
          <div class="topggbg">Circuit</div>
          <div class="twolr">
            <div class="leftggl">
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
              <div class="ggr"><s:date format="yyyy-MM-dd"
										name="vendorCircuit.firstInvoiceDate" /></div>
                                         <div class="ggclean"></div>
<%--              <div class="ggr">${lastInvoiceDate}</div>--%>
               <div class="ggclean"></div>
              </li>
              <li>
              <div class="ggl">Latest Invoice Date</div>
<%--              <div class="ggr">${firstInvoiceDate}</div>--%>
              <div class="ggr"><s:date format="yyyy-MM-dd"
										name="vendorCircuit.latestInvoiceDate" /></div>
                                         <div class="ggclean"></div>
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
              <div class="toplggr" id="topheight">
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
                  <div class="ggl ggadl datass" style="min-height:177px;" id="lefth">
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
          <div class="ggbgtop datass" style="min-height: 175px;">
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
    <div style="width:100%; height:40px; overflow:hidden;">
      <div style="float: left; margin-top:10px;">
        <input type="button" onClick="closeCircuitDetail()" value="Close"/>
        <!--&nbsp;
        <input type="button" id="saveQueryButton" value="Save" />-->
      </div>
    </div>
  </div>
</div>
