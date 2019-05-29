<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<SCRIPT type="text/javascript"
	src="include/javascript/ccm/common/common.js"></SCRIPT>
<script src="include/javascript/saninco/downLoad.js" type="text/javascript"></script>


    <style>
    .xzdown45 dl{
max-width:1070px;  }
.xzdown45 dl dd{
float: left;
    width: 345px;
    margin-right: 10px;}
    </style>



<div class="update-ReportAdminTags" style="width:1100px; border-top:1px #666666 solid;border-bottom:1px #666666 solid;" id="circuitDetail">
  <div class="tabForm">
    <div class="Cir_Det" id = "heightID">
    
      
      
         <!--start yes-->
      
      <div class="ggcon" id="allheight">
       <div class="topggbg">Cost Detail</div>
      <div class="sgk3">
       <div class="sgkle3"><ul>
       <li><div class="sgkjuzuo2">Contract_ID(Primary KEY)</div><div class="sgkjuyou2">${contract.id}</div></li>
        <li><div class="sgkjuzuo2">Circuit_ID</div><div class="sgkjuyou2">${contract.circuitId}</div></li>
        <li><div class="sgkjuzuo2">Project Vendor Requestor</div><div class="sgkjuyou2">${contract.vendorName}</div></li>
        <li><div class="sgkjuzuo2">Month to Month</div><div class="sgkjuyou2">${contract.monthToMonth}</div></li>
        <li><div class="sgkjuzuo2">Contract Start</div><div class="sgkjuyou2"><fmt:formatDate value="${contract.contractStart}" type="date"/></div></li>
        <li><div class="sgkjuzuo2">Contract End</div><div class="sgkjuyou2"><fmt:formatDate value="${contract.contractEnd}" type="date"/></div></li>
        <li><div class="sgkjuzuo2">Name of Agreenment</div><div class="sgkjuyou2">${contract.nameOfAgreement}</div></li>
        <li><div class="sgkjuzuo2">Service Term</div><div class="sgkjuyou2">${contract.serviceTerm}</div></li>
       <div style="clear:both;"></div>
       </ul></div>
        <div class="sgkmid3"><ul>
       <li><div class="sgkjuzuo2">Renewal Term</div><div class="sgkjuyou2">${contract.renewalTerm}</div></li>
       <li><div class="sgkjuzuo2">Purpose</div><div class="sgkjuyou2">${contract.purpose}</div></li>
        <li><div class="sgkjuzuo2">Service Product Description</div><div class="sgkjuyou2">${contract.serviceProductDescription}</div></li>
        <li><div class="sgkjuzuo2">Description</div><div class="sgkjuyou2">${contract.description}</div></li>
        <li><div class="sgkjuzuo2">Source</div><div class="sgkjuyou2">${contract.source}</div></li>
         <li><div class="sgkjuzuo2">Commitment</div><div class="sgkjuyou2">${contract.commitment}</div></li>
         <li><div class="sgkjuzuo2">Revenue</div><div class="sgkjuyou2">${contract.revenue}</div></li>
         <li><div class="sgkjuzuo2">Termination</div><div class="sgkjuyou2">${contract.termination}</div></li>
         <li><div class="sgkjuzuo2">Rogers End-User</div><div class="sgkjuyou2">${contract.rogersEndUser}</div></li>
       <div style="clear:both;"></div>
       </ul></div>
         <div class="sgkrg3"><ul>
       <li><div class="sgkjuzuo2">Customer Term Key Terms</div><div class="sgkjuyou2">${contract.customerTermKeyTerm}</div></li>
       <li><div class="sgkjuzuo2">Comments Term</div><div class="sgkjuyou2">${contract.commentsTerm}</div></li>
       <li><div class="sgkjuzuo2">Sheet</div><div class="sgkjuyou2">${contract.sheet}</div></li>
       <li><div class="sgkjuzuo2">Contract</div><div class="sgkjuyou2">${contract.contract}</div></li>
        <li><div class="sgkjuzuo2">ASR</div><div class="sgkjuyou2">${contract.asr}</div></li>
        <li><div class="sgkjuzuo2">Status</div><div class="sgkjuyou2">${contract.status}</div></li>
       <div style="clear:both;"></div>
       </ul></div>
         <div style="clear:both;"></div>
      </div>
      
     <div class="xzdown45">
     <dl>
     <dt>File Download</dt> 
              <c:forEach items="${Attachment.file}" var="jl" varStatus="i">
              <dd>${jl.fileName}<span class="tbdow" onClick="Download('${jl.fileName}','${jl.filePath}');"></span></dd>
              </c:forEach> 
               <div style="clear:both;"></div></dl>
     </div>
        </div>
        <!--leftggcon-->

      <!--start no-->
            </div>
              <form id="Download" action="download.action" method="post" style="display: none;">
	          <input type="hidden" name="filePath" value=""/>
		      <input type="hidden" name="fileName" value=""/>
	          </form>
            
    <div style="width:100%; height:40px; overflow:hidden;">
      <div style="float: left; margin-top:10px;">
        <input type="button" onClick="closeCircuitDetail()" value="Close"/>
        <!--&nbsp;
        <input type="button" id="saveQueryButton" value="Save" />-->
      </div>
    </div>
  </div>
</div>
