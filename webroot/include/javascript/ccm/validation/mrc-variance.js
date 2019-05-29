
/**
 * For MRC Variance module.
 */

function MRCVariance() {

    this.varianceListPage = null;
    this.invoiceId = window.invoiceId;
    this.strippedCircuitNumber = '';
    this.varianceListPageFilter = null;

    this.STATUS_VALIDATION_TYPE = 'Status Validation';
    this.RATE_VALIDATION_TYPE = 'Rate Validation';

    this.PANEL_VALIDATION_RESULT_LOCATION = 'PANEL';
    this.LIST_VALIDATION_RESULT_LOCATION = 'LIST';
}

/**
 * Add 'Variance' tab button.
 * @param {[type]} accordionIdAttr [description]
 */
MRCVariance.prototype.addVarianceTabButtonToBrowseAccordion = function() {

    var self = this;

    var varianceLiElem = '<li title="" class="">';
    varianceLiElem += '<a href="javascript: void(0);" onclick="window.mrcVariance.switchTab(this)"><em>Variance</em></a>';
    varianceLiElem += '</li>';
    
    $('#tab_Browse').find('ul').append(varianceLiElem);

}

/**
 * Switch to 'Variance' Tab and fill the data table.
 * @param  {HTMLElement} elem [description]
 * @return {[type]}      [description]
 */
MRCVariance.prototype.switchTab = function(elem) {

    var self = this;

    // Focus the tab style
    $(elem).parents('ul').find('li').each(function() {
        $(this).attr('title', '').removeClass();
    });

    $(elem).parent().attr('title', 'active').addClass('selected');

    self.generateVarianceDataTable();
}

MRCVariance.prototype.generateVarianceDataTable = function() {

    var self = this;

    var tableContainer = '<div class="mrc-variance-content">';
    tableContainer +=       '<table class="list-wrapper-outer-table">';
    tableContainer +=           '<tr>';
    tableContainer +=               '<td>';
    tableContainer +=                   '<div class="list-wrapper-container">';
    tableContainer +=                       '<div id="variance-table"></div>';
    tableContainer +=                   '</div>';
    tableContainer +=               '</td>';
    tableContainer +=           '</tr>';
    tableContainer +=           '<tr>';
    tableContainer +=               '<td>';
    tableContainer +=                   '<div id="variance-table-pagination"></div>';
    tableContainer +=                   '<div id="variance-btns"></div>';
    tableContainer +=               '</td>';
    tableContainer +=           '</tr>';
    tableContainer +=       '</table>';
    tableContainer += ' </div>';

    $('#tab_Browse #__tab00 > *').each(function() {
        $(this).css('display', 'none');
    });

    // Add table struc.
    $('#tab_Browse #__tab00').append(tableContainer);

    self.addOperateBtns();

    self.renderDataTable();

}

/**
 * Request data and render variance table.
 * @return {[type]} [description]
 */
MRCVariance.prototype.renderDataTable = function() {

    var self = this;

    window.varianceListPage = new SANINCO.Page('variance-table', 'varianceListPage', {
        sortingField : "r.strippedCircuitNumber",
        sortingDirection : "asc",
        vo : "searchInvoiceVO",
        autoToTop : true,
        recordText: 'Variance Results:',
        totalPageUri : 'countMRCVarianceNo.action',
        dataUri : 'listMRCVarianceListings.action',
        paginationDiv : "variance-table-pagination",
        recPerArray : [10,20,30,50,100,500,1000],
        cols : [
            {
                title: 'Stripper Circuit Number', 
                dataField: function(o) {
                    return '<a href="javascript: void(0)">'+ o.strippedCircuitNumber +'</a>'
                },
                sort: 'r.strippedCircuitNumber',
                filtration: {
                    name: 'r.strippedCircuitNumber',
                    alias: 'Stripper Circuit Number'
                }
                

            },

            {
                title: 'Previous MRC', 
                dataField: 'previousMRC',
                sort: 'r.previousMRC',
                filtration: {
                    name: 'r.previousMRC',
                    alias: 'Previous MRC'
                },
                className: 'align-right'

            },

            {
                title: 'Current MRC', 
                dataField: 'currentMRC',
                sort: 'r.currentMRC',
                filtration: {
                    name: 'r.currentMRC',
                    alias: 'Current MRC'
                }

            },

            {
                title: 'MRC Variance', 
                dataField: 'mrcVariance',
                sort: 'r.mrcVariance',
                filtration: {
                    name: 'r.mrcVariance',
                    alias: 'MRC Variance'
                }

            },

            {
                title: 'OCC Amount', 
                dataField: 'occAmount',
                sort: 'r.occAmount',
                filtration: {
                    name: 'r.occAmount',
                    alias: 'OCC Amount'
                }

            },

            {
                title: 'Status Validation', 
                dataField: function(o) {
                    return self.generateVarianceValidationElem(o, self.STATUS_VALIDATION_TYPE, self.LIST_VALIDATION_RESULT_LOCATION);
                },
                sort: 'r.statusValidationId',
                filtration: {
                    name: 'r.statusValidationId',
                    alias: self.filtrationAlias(self.STATUS_VALIDATION_TYPE)
                }

            },

            {
                title: 'Rate Validation', 
                dataField: function(o) {
                    return self.generateVarianceValidationElem(o, self.RATE_VALIDATION_TYPE, self.LIST_VALIDATION_RESULT_LOCATION);
                },
                sort: 'r.rateValidationId',
                filtration: {
                    name: 'r.rateValidationId',
                    alias: self.filtrationAlias(self.RATE_VALIDATION_TYPE)
                }

            },

            {
                title: 'Variance Reason', 
                dataField: 'varianceReason',
                sort: 'r.varianceReason',
                filtration: {
                    name: 'r.varianceReason',
                    alias: 'Variance Reason'
                }

            },

            {
                title: 'Action', 
                dataField: function(o) {
                    return self.generateOperationButtonCell(o);
                }
            }
        ] 
    });

    self.varianceListPage = window.varianceListPage;

    self.varianceListPage.addTotalSuccessEvent(function(data) {

        if ( data.totalResultNo <= 0 && !$('#variance-btns').is(':hidden') ) {
            $('#variance-btns').hide();
        } else {

            if ($('#variance-btns').is(':hidden')) {
                $('#variance-btns').show();
            }
            
        }

    });

    // Set the param 
    self.varianceListPage.voParam = {
        invoiceId: self.invoiceId
    }

    self.addVarianceFilter(self.varianceListPage);

    self.varianceListPage.start();

}

MRCVariance.prototype.changeVarianceTab = function(elem) {

    var self = this;
    var bindContent = elem.dataset.bind;

    $(elem).parents('.yui-nav').find('li').each(function() {
        $(this).removeClass('selected');
    });

    $(elem).addClass('selected');

    if (bindContent == 'item-variance') {

        

        self.requestVarianceItemsList();
    }

    if (bindContent == 'two-month-items') {
        $('#item-variance').hide();
        $('#two-month-items').show();

        self.requestTwoMonthsItemsList();
    }
}

MRCVariance.prototype.requestVarianceItemsList = function() {

    var self = this;

    window.itemVarianceListPage = new SANINCO.Page('item-variance-list', 'itemVarianceListPage', {
        sortingField : "r.audit_status_id",
        sortingDirection : "asc",
        vo : "searchInvoiceVO",
        autoToTop : true,
        recordText: 'Variance Items Results:',
        totalPageUri : 'countVarianceItemsNo.action',
        dataUri : 'listVarianceItemsListings.action',
        paginationDiv : "item-variance-list-pagination",
        recPerArray : [10,20,30,50,100,500,1000],
        cols: [
            
            {
                title: 'Rate Validation', 
                dataField: function(o) {
                    return window.validationStatus(o);
                },
                sort: 'r.audit_status_id',
                filtration: {
                    name: 'r.audit_status_id',
                    alias: window.filtrationAlias()
                }
            },

            {
                title: 'Stripped Circuit Number', 
                dataField: 'strippedCircuitNumber',
                sort: 'r.strippedCircuitNumber',
                filtration: {
                    name: 'r.strippedCircuitNumber',
                    alias: 'Stripped Circuit Number'
                },

            },

            {
                title: 'Charge Type', 
                dataField: 'chargeType',
                sort: 'r.chargeType',
                filtration: {
                    name: 'r.chargeType',
                    alias: 'Charge Type'
                },

            },

            {
                title: 'Current Amount', 
                dataField: 'currentAmount',
                sort: 'r.currentAmount',
                filtration: {
                    name: 'r.currentAmount',
                    alias: 'Current Amount'
                },

            },

            {
                title: 'Previous Amount', 
                dataField: 'previousAmount',
                sort: 'r.previousAmount',
                filtration: {
                    name: 'r.previousAmount',
                    alias: 'Previous Amount'
                },

            },

            {
                title: 'Item Description', 
                dataField: 'itemDescription',
                sort: 'r.itemDescription',
                filtration: {
                    name: 'r.itemDescription',
                    alias: 'Item Description'
                },

            },

            {
                title: 'USOC', 
                dataField: 'usoc',
                sort: 'r.usoc',
                filtration: {
                    name: 'r.usoc',
                    alias: 'USOC'
                },

            },

            {
                title: 'Line Item Code', 
                dataField: 'lineItemCode',
                sort: 'r.lineItemCode',
                filtration: {
                    name: 'r.lineItemCode',
                    alias: 'Line Item Code'
                },

            },

            {
                title: 'Line Item Code Description', 
                dataField: 'lineItemCodeDescription',
                sort: 'r.lineItemCodeDescription',
                filtration: {
                    name: 'r.lineItemCodeDescription',
                    alias: 'Line Item Code Description'
                },

            },

            {
                title: 'From Date', 
                dataField: 'fromDate',
                sort: 'r.fromDate',
                filtration: {
                    name: 'r.fromDate',
                    alias: 'From Date'
                },

            },

            {
                title: 'To Date', 
                dataField: 'toDate',
                sort: 'r.toDate',
                filtration: {
                    name: 'r.toDate',
                    alias: 'To Date'
                },

            },

            {
                title: 'Reference Type', 
                dataField: 'referenceType',
                sort: 'r.referenceType',
                filtration: {
                    name: 'r.referenceType',
                    alias: 'Reference Type'
                },

            },

            {
                title: 'Rate', 
                dataField: 'rate',
                sort: 'r.rate',
                filtration: {
                    name: 'r.rate',
                    alias: 'Rate'
                },

            },

            {
                title: 'Rate Effective Date', 
                dataField: 'rateEffectiveDate',
                sort: 'r.rateEffectiveDate',
                filtration: {
                    name: 'r.rateEffectiveDate',
                    alias: 'Rate Effective Date'
                },

            },

            {
                title: 'Rate Mode', 
                dataField: 'rateMode',
                sort: 'r.rateMode',
                filtration: {
                    name: 'r.rateMode',
                    alias: 'Rate Mode'
                },

            },

            {
                title: 'Product', 
                dataField: 'product',
                sort: 'r.product',
                filtration: {
                    name: 'r.product',
                    alias: 'Product'
                },

            },

            {
                title: 'Sub Product', 
                dataField: 'subProduct',
                sort: 'r.subProduct',
                filtration: {
                    name: 'r.subProduct',
                    alias: 'Sub Product'
                },

            },

            {
                title: 'Tariff File Name', 
                dataField: 'tariffFileName',
                sort: 'r.tariffFileName',
                filtration: {
                    name: 'r.tariffFileName',
                    alias: 'Tariff File Name'
                },

            },

            {
                title: 'Tariff Page', 
                dataField: 'tariffPage',
                sort: 'r.tariffPage',
                filtration: {
                    name: 'r.tariffPage',
                    alias: 'Tariff Page'
                },

            },

            {
                title: 'Tariff Part Section', 
                dataField: 'tariffPartSection',
                sort: 'r.tariffPartSection',
                filtration: {
                    name: 'r.tariffPartSection',
                    alias: 'Tariff Part Section'
                },

            },

            {
                title: 'Item Number', 
                dataField: 'itemNumber',
                sort: 'r.itemNumber',
                filtration: {
                    name: 'r.itemNumber',
                    alias: 'Item Number'
                },

            },

            {
                title: 'CRTC Number', 
                dataField: 'crtcNumber',
                sort: 'r.crtcNumber',
                filtration: {
                    name: 'r.crtcNumber',
                    alias: 'CRTC Number'
                },

            },

            {
                title: 'Rule Details', 
                dataField: 'ruleDetails',
                sort: 'r.ruleDetails',
                filtration: {
                    name: 'r.ruleDetails',
                    alias: 'Rule Details'
                },

            },

            {
                title: 'Contract File Name', 
                dataField: 'contractFileName',
                sort: 'r.contractFileName',
                filtration: {
                    name: 'r.contractFileName',
                    alias: 'Contract File Name'
                },

            },

            {
                title: 'Contract Term Months', 
                dataField: 'contractTermMonth',
                sort: 'r.contractTermMonth',
                filtration: {
                    name: 'r.contractTermMonth',
                    alias: 'Contract Term Months'
                },

            },

            {
                title: 'Contract Service  Schedule Name', 
                dataField: 'contractServiceScheduleName',
                sort: 'r.contractServiceScheduleName',
                filtration: {
                    name: 'r.contractServiceScheduleName',
                    alias: 'Contract Service  Schedule Name'
                },

            },

            {
                title: 'Contract Early Ternimation Fee', 
                dataField: 'contractEarlyTerminationFee',
                sort: 'r.contractEarlyTerminationFee',
                filtration: {
                    name: 'r.contractEarlyTerminationFee',
                    alias: 'Contract Early Ternimation Fee'
                },

            },
        ]
    });

    self.itemVarianceListPage = window.itemVarianceListPage;

    self.itemVarianceListPage.addTotalSuccessEvent(function() {
        $('#two-month-items').hide();
        $('#item-variance').show();
    });

    self.itemVarianceListPage.voParam = {
        invoiceId: self.invoiceId,
        cricuitNumber: self.strippedCircuitNumber
    }

    self.addVarianceItemsFilter( self.itemVarianceListPage );

    self.itemVarianceListPage.start();

}

MRCVariance.prototype.addVarianceItemsFilter = function(listPage) {

    window.varianceItemsListPageFilter = new SANINCO.Filter();
    self.varianceItemsListPageFilter = window.varianceItemsListPageFilter;

    self.varianceItemsListPageFilter.addEditeEvent(function() {
        listPage.start();
    }); 

    self.varianceItemsListPageFilter.add('r.audit_status_id', 'Number');
    self.varianceItemsListPageFilter.add('r.strippedCircuitNumber', 'String');
    self.varianceItemsListPageFilter.add('r.chargeType', 'String');
    self.varianceItemsListPageFilter.add('r.currentAmount', 'Number');
    self.varianceItemsListPageFilter.add('r.previousAmount', 'Number');
    self.varianceItemsListPageFilter.add('r.itemDescription', 'String');
    self.varianceItemsListPageFilter.add('r.usoc', 'String');
    self.varianceItemsListPageFilter.add('r.lineItemCode', 'String');
    self.varianceItemsListPageFilter.add('r.lineItemCodeDescription', 'String');
    self.varianceItemsListPageFilter.add('r.fromDate', 'String');
    self.varianceItemsListPageFilter.add('r.toDate', 'String');

    self.varianceItemsListPageFilter.add('r.referenceType', 'String');
    self.varianceItemsListPageFilter.add('r.rate', 'Number');
    self.varianceItemsListPageFilter.add('r.rateEffectiveDate', 'String');
    self.varianceItemsListPageFilter.add('r.rateMode', 'String');
    self.varianceItemsListPageFilter.add('r.product', 'String');
    self.varianceItemsListPageFilter.add('r.subProduct', 'String');
    self.varianceItemsListPageFilter.add('r.tariffFileName', 'String');
    self.varianceItemsListPageFilter.add('r.tariffPage', 'String');
    self.varianceItemsListPageFilter.add('r.tariffPartSection', 'String');
    self.varianceItemsListPageFilter.add('r.itemNumber', 'String');
    self.varianceItemsListPageFilter.add('r.crtcNumber', 'String');
    self.varianceItemsListPageFilter.add('r.ruleDetails', 'String');
    self.varianceItemsListPageFilter.add('r.contractFileName', 'String');
    self.varianceItemsListPageFilter.add('r.contractTermMonth', 'String');
    self.varianceItemsListPageFilter.add('r.contractServiceScheduleName', 'String');
    self.varianceItemsListPageFilter.add('r.contractEarlyTerminationFee', 'String');

    listPage.setFilter(self.varianceItemsListPageFilter);

}

MRCVariance.prototype.requestTwoMonthsItemsList = function() {
    
    var self = this;

    window.twoMonthsVarianceItemsListPage = new SANINCO.Page('two-month-items-list', 'twoMonthsVarianceItemsListPage', {
        sortingField : "r.proposalId",
        sortingDirection : "asc",
        vo : "searchInvoiceVO",
        autoToTop : true,
        recordText: 'Variance Proposal Items Results:',
        totalPageUri : 'countTwoMonthsVarianceProposalItemsNo.action',
        dataUri : 'listTwoMonthsVarianceProposalItemsListings.action',
        paginationDiv : "two-month-items-list-pagination",
        recPerArray : [10,20,30,50,100,500,1000],
        cols: [
            {
                title: 'Proposal Id', 
                dataField: 'proposalId',
                sort: 'r.proposalId',
                filtration: {
                    name: 'r.proposalId',
                    alias: 'Proposal Id'
                }
            },

            {
                title: 'Item Name', 
                dataField: 'itemName',
                sort: 'r.itemName',
                filtration: {
                    name: 'r.itemName',
                    alias: 'Item Name'
                },

            },

            {
                title: 'Payment Amount', 
                dataField: 'paymentAmount',
                sort: 'r.paymentAmount',
                filtration: {
                    name: 'r.paymentAmount',
                    alias: 'Payment Amount'
                },

            },

            {
                title: 'Dispute Amount', 
                dataField: 'disputeAmount',
                sort: 'r.disputeAmount',
                filtration: {
                    name: 'r.disputeAmount',
                    alias: 'Dispute Amount'
                },

            },

            {
                title: 'Credit Amount', 
                dataField: 'creditAmount',
                sort: 'r.creditAmount',
                filtration: {
                    name: 'r.creditAmount',
                    alias: 'Credit Amount'
                },

            },

            {
                title: 'SCOA', 
                dataField: 'scoa',
                sort: 'r.scoa',
                filtration: {
                    name: 'r.scoa',
                    alias: 'SCOA'
                },

            },

            {
                title: 'Circuit Number', 
                dataField: 'circuitNumber',
                sort: 'r.circuitNumber',
                filtration: {
                    name: 'r.circuitNumber',
                    alias: 'Circuit Number'
                },

            },

            {
                title: 'Stripped Circuit Number', 
                dataField: 'strippedCircuitNumber',
                sort: 'r.strippedCircuitNumber',
                filtration: {
                    name: 'r.strippedCircuitNumber',
                    alias: 'Stripped Circuit Number'
                },

            },

            {
                title: 'Product', 
                dataField: 'product',
                sort: 'r.product',
                filtration: {
                    name: 'r.product',
                    alias: 'Product'
                },

            },

            {
                title: 'Component', 
                dataField: 'component',
                sort: 'r.component',
                filtration: {
                    name: 'r.component',
                    alias: 'Component'
                },

            },

            {
                title: 'From Date', 
                dataField: 'fromDate',
                sort: 'r.fromDate',
                filtration: {
                    name: 'r.fromDate',
                    alias: 'From Date'
                },

            },

            {
                title: 'To Date', 
                dataField: 'toDate',
                sort: 'r.toDate',
                filtration: {
                    name: 'r.toDate',
                    alias: 'To Date'
                },

            },

            {
                title: 'Billing Number', 
                dataField: 'billingNumber',
                sort: 'r.billingNumber',
                filtration: {
                    name: 'r.billingNumber',
                    alias: 'Billing Number'
                },

            },

            {
                title: 'Quantity', 
                dataField: 'quantity',
                sort: 'r.quantity',
                filtration: {
                    name: 'r.quantity',
                    alias: 'Quantity'
                },

            },

            {
                title: 'USOC', 
                dataField: 'usoc',
                sort: 'r.usoc',
                filtration: {
                    name: 'r.usoc',
                    alias: 'USOC'
                },

            },

            {
                title: 'USOC Description', 
                dataField: 'usocDescription',
                sort: 'r.usocDescription',
                filtration: {
                    name: 'r.usocDescription',
                    alias: 'USOC Description'
                },

            },

            {
                title: 'Line Item Code', 
                dataField: 'lineItemCode',
                sort: 'r.lineItemCode',
                filtration: {
                    name: 'r.lineItemCode',
                    alias: 'Line Item Code'
                },

            },

            {
                title: 'Line Item Code Description', 
                dataField: 'lineItemCodeDescription',
                sort: 'r.lineItemCodeDescription',
                filtration: {
                    name: 'r.lineItemCodeDescription',
                    alias: 'Line Item Code Description'
                },

            }
        ]
    });

    self.twoMonthsVarianceItemsListPage = window.twoMonthsVarianceItemsListPage;

    self.twoMonthsVarianceItemsListPage.voParam = {
        invoiceId: self.invoiceId,
        cricuitNumber: self.strippedCircuitNumber
    }

    self.twoMonthsVarianceItemsListPage.addTotalSuccessEvent(function() {
        $('#item-variance').hide();
        $('#two-month-items').show();
    });

    self.addTwoMonthsVarianceItemsFilter( self.twoMonthsVarianceItemsListPage );

    self.twoMonthsVarianceItemsListPage.start();

}

MRCVariance.prototype.addTwoMonthsVarianceItemsFilter = function(listPage) {

    window.twoMonthsVarianceItemsListPageFilter = new SANINCO.Filter();
    self.twoMonthsVarianceItemsListPageFilter = window.twoMonthsVarianceItemsListPageFilter;

    self.twoMonthsVarianceItemsListPageFilter.addEditeEvent(function() {
        listPage.start();
    }); 

    self.twoMonthsVarianceItemsListPageFilter.add('r.proposalId', 'Number');
    self.twoMonthsVarianceItemsListPageFilter.add('r.itemName', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.paymentAmount', 'Number');
    self.twoMonthsVarianceItemsListPageFilter.add('r.disputeAmount', 'Number');
    self.twoMonthsVarianceItemsListPageFilter.add('r.creditAmount', 'Number');
    self.twoMonthsVarianceItemsListPageFilter.add('r.scoa', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.circuitNumber', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.strippedCircuitNumber', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.product', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.component', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.fromDate', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.toDate', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.billingNumber', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.quantity', 'Number');
    self.twoMonthsVarianceItemsListPageFilter.add('r.usoc', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.usocDescription', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.lineItemCode', 'String');
    self.twoMonthsVarianceItemsListPageFilter.add('r.lineItemCodeDescription', 'String');

    listPage.setFilter(self.twoMonthsVarianceItemsListPageFilter);

}

/**
 * Add operateing buttons for variance table.
 */
MRCVariance.prototype.addOperateBtns = function() {

    var self = this;
    var btnContainer = $('#variance-btns');

    /*$('<input type="button" value="Download Master Inventory" class="download-MT">')
        .click(function() {
            // throw new Error('this is good!');
        })
        .appendTo( btnContainer );

    $('<input type="button" value="Download Rate Rule Template" class="variance-revalidate">')
        .click(function() {
            // throw new Error('this is good!');
        })
        .appendTo( btnContainer );

    $('<input type="button" value="Re-Validate" class="variance-revalidate">')
        .click(function() {
            // throw new Error('this is good!');
        })
        .appendTo( btnContainer );

    $('<input type="button" value="Re-Validate" class="variance-revalidate">')
        .click(function() {
            // throw new Error('this is good!');
        })
        .appendTo( btnContainer );

    $('<input type="button" value="Re-Validate" class="variance-revalidate">')
        .click(function() {
            // throw new Error('this is good!');
        })
        .appendTo( btnContainer );

    $('<input type="button" value="Re-Validate" class="variance-revalidate">')
        .click(function() {
            // throw new Error('this is good!');
        })
        .appendTo( btnContainer );*/

    $('<input type="button" value="Re-Validate" class="variance-revalidate">')
        .click(function() {
            // throw new Error('this is good!');
        })
        .appendTo( btnContainer );

}

/**
 * Add mrc variance list filter.
 * @param {[type]} startPage [description]
 */
MRCVariance.prototype.addVarianceFilter = function(startPage) {
    
    var self = this;

    window.varianceListPageFilter = new SANINCO.Filter();
    self.varianceListPageFilter = window.varianceListPageFilter;

    self.varianceListPageFilter.addEditeEvent(function() {
        startPage.start();
    }); 

    self.varianceListPageFilter.add('r.strippedCircuitNumber', 'String');
    self.varianceListPageFilter.add('r.previousMRC', 'Number');
    self.varianceListPageFilter.add('r.currentMRC', 'Number');
    self.varianceListPageFilter.add('r.mrcVariance', 'Number');
    self.varianceListPageFilter.add('r.occAmount', 'Number');
    self.varianceListPageFilter.add('r.statusValidationId', 'Number');
    self.varianceListPageFilter.add('r.rateValidationId', 'Number');
    self.varianceListPageFilter.add('r.varianceReason', 'String');

    startPage.setFilter(self.varianceListPageFilter);

}

/**
 * Clicking other 'Browse' tab buttons except 'Variance'
 * @param  {[type]} accordionName [description]
 * @return {[type]}               [description]
 */
MRCVariance.prototype.actionsForTabButtonEventExceptVariance = function(accordionName) {

    if (accordionName == "Browse") {

        $('#tab_Browse #__tab00 > *').each(function() {

            if ( $(this).is(':hidden') ) {
                $(this).css('display', 'inherit');
            }

            if( $(this).hasClass('mrc-variance-content') ) {
                $(this).remove();
            }
            
        });

    }
    
}

/**
 * Generating variance validation result UI.
 * @param  {Object} o              Variance table data object.
 * @param  {String} validationType [description]
 * @return {[type]}                [description]
 */
MRCVariance.prototype.generateVarianceValidationElem = function(o, validationType, placeToShow) {

    var self = this;
    var returnElemStr = '';
    var switchExpression = 0; // {Number}
    var auditStatusName = '';

    // Type of validation: circuit or rate.
    switch (validationType) {

        case self.STATUS_VALIDATION_TYPE:

            switchExpression = parseInt(o.statusValidationId);
            auditStatusName = o.statusValidationName;
            break;

        case self.RATE_VALIDATION_TYPE:

            switchExpression = parseInt(o.rateValidationId);
            auditStatusName = o.rateValidationName;
            break;
    }

    // The place to show validation result: variance list or variance panel.
    switch(placeToShow) {

        case self.LIST_VALIDATION_RESULT_LOCATION:
            returnElemStr += '<div class="validation-status" \
                        data-validation-type="'+ validationType +'"\
                        data-stripped-circuit-number="'+ o.strippedCircuitNumber +'"\
                        onclick="window.mrcVariance.showVarianceValidationResult(this)">';
            break;

        case self.PANEL_VALIDATION_RESULT_LOCATION:
            returnElemStr += '<div class="validation-status">';
            break;
    }

    // Differ audit result: Passed, Failed or Cannot validate.
    switch ( switchExpression ) {
        case 1:
            returnElemStr += '  <span class="icon pass"></span> ';
            break;
        case 2:
            returnElemStr += '  <span class="icon failed"></span> ';
            break;
        case 3:
            returnElemStr += '  <span class="icon can-not-validate"></span> ';
            break;
        default:
            returnElemStr += '  <span class="icon no-reference"></span> ';
    }

    returnElemStr += auditStatusName;
    returnElemStr += ' </div>';

    return returnElemStr;

}

/**
 * Constructing validation result filter alias UI.
 * @param  {[type]} validationType [description]
 * @return {[type]}                [description]
 */
MRCVariance.prototype.filtrationAlias = function(validationType) {

    var self = this;
    var validationTitle = validationType == self.STATUS_VALIDATION_TYPE ? 'Status Validation' : 'Rate Validation';
    var returnStr = '';

    returnStr += validationTitle + '<br />';
    returnStr += ' <div style=color:gray> ';
    returnStr +=    '<i> Please use the = in front of the letter filter <br />';
    returnStr +=        '1=Passed <br />';
    returnStr +=        '2=Failed <br />';
    returnStr +=        '3=Cannot Validate <br />';
    returnStr +=    '</i> ';
    returnStr += ' </div>';

    return returnStr;
}

/**
 * Show validation result details.
 * @param  {[type]} Elem [description]
 * @return {[type]}      [description]
 */
MRCVariance.prototype.showVarianceValidationResult = function(elem) {

    var self = this;
    var validationType = elem.dataset.validationType;
    var strippedCircuitNumber = elem.dataset.strippedCircuitNumber;

    self.selectRow(elem);

    if ( validationType == self.STATUS_VALIDATION_TYPE ) {
        window.showValidationResult(null, self.invoiceId, 'Exists', null, strippedCircuitNumber);
    } else {
        window.showValidationResult(null, self.invoiceId, null, null, strippedCircuitNumber);
    }
    
 
}

/**
 * Constructing 'Action' cell UI of variance list.
 * @param  {[type]} o [description]
 * @return {[type]}   [description]
 */
MRCVariance.prototype.generateOperationButtonCell = function(o) {

    var self = this;
    var returnStr = '';
    self.strippedCircuitNumber = o.strippedCircuitNumber;

    returnStr += '<div class="variance-action" \
                data-stripped-circuit-number="'+ o.strippedCircuitNumber +'" \
                data-status-validation-id="'+ o.statusValidationId +'" \
                data-status-validation-name="'+ o.statusValidationName +'" \
                data-rate-validation-id="'+ o.rateValidationId +'" \
                data-rate-validation-name="'+ o.rateValidationName +'" \
                data-variance-reason="'+ o.varianceReason +'" \
                onclick="window.mrcVariance.showCircuitDetailsLayer(this)">';
    returnStr +=    '<i class="icon operate"></i>';
    returnStr +=    'Operate';
    returnStr += '</div>';

    return returnStr;
}

/**
 * Select the row when click validation result cell.
 * @param  {HTMLElement} domElem [description]
 * @return {[type]}         [description]
 */
MRCVariance.prototype.selectRow = function(domElem) {

    $(domElem).parents('tbody').find('tr').each(function() {

        if ( $(this).hasClass('selectStyle') ) {
            $(this).removeClass('selectStyle');
        }
    });

    $(domElem).parents('tr').addClass('selectStyle');
}

/**
 * Show circuit details layer when click 'operate' button.
 * @param  {[type]} elem [description]
 * @return {[type]}      [description]
 */
MRCVariance.prototype.showCircuitDetailsLayer = function(elem) {

    var self = this;
    var echoObj = elem.dataset;
    var statusValidationUI = self.generateVarianceValidationElem(echoObj, self.STATUS_VALIDATION_TYPE, self.PANEL_VALIDATION_RESULT_LOCATION);

    var rateValidationUI = self.generateVarianceValidationElem(echoObj, self.RATE_VALIDATION_TYPE, self.PANEL_VALIDATION_RESULT_LOCATION);

    $('.circuit-layer .circuit-number-value').text( echoObj.strippedCircuitNumber );
    $('.circuit-layer .status-validation-UI').html(statusValidationUI);
    $('.circuit-layer .rate-validation-UI').html(rateValidationUI);

    $('.variance-hidden').css('display', 'none');

    if ( $('.circuit-layer').is(':hidden') ) {
        $('.circuit-layer').css('display', 'block');
    }

    self.circuitDetailsRequest(echoObj);
    
}

/**
 * Circuit details layer request actions.
 * @param  {[type]} options [description]
 * @return {[type]}         [description]
 */
MRCVariance.prototype.circuitDetailsRequest = function(options) {

    var self = this;

    self.requestVarianceReasons(options);

    self.requestCircuitInformation(options);

    self.requestVarianceItemsList();
    
}

/**
 * Obtain variance reasons.
 * @param  {[type]} options [description]
 * @return {[type]}         [description]
 */
MRCVariance.prototype.requestVarianceReasons = function(options) {

    var self = this;

    $.ajax({
        type: 'GET',
        url: 'getVarianceReasons.action',
        dataType: 'text',
        success: function(data) {

            var varianceReasons = JSON.parse( data );
            var varianceReasonsObj = {};
            
            if ( Array.isArray(varianceReasons) ) {

                varianceReasonsObj.total = varianceReasons.length;
                varianceReasonsObj.results = [];
                varianceReasonsObj.results.push({id: 'all', name: ''});

                // 遍历添加 items.
                $.each(varianceReasons, function(i, v) {
                    
                    varianceReasonsObj.results.push({
                        id: v.key,
                        name: v.value
                    });
                });
            }
            // 阻止数据结构
            
            // 销毁组件。
            $('#variance-reason-list').html('');

            // 渲染 flexbox dropdown list.
            self.varianceReasonsDropdownList = $('#variance-reason-list').flexbox(varianceReasonsObj, {
                maxCacheBytes : 200000,
                highlightMatches : true,
                autoCompleteFirstMatch : false,
                paging: false,
                width : 240,
                onSelect: function(){}
            });

            self.varianceReasonsDropdownList.setValue( options.varianceReason );

        },
        error: function() {
            throw new Error('Get variance reasons failed!');
        }
    });

}

/**
 * Request data and render circuit information section.
 * @param  {[type]} options [description]
 * @return {[type]}         [description]
 */
MRCVariance.prototype.requestCircuitInformation = function(options) {

    var self = this;
    var queryString = '&searchInvoiceVO.cricuitNumber=' + options.strippedCircuitNumber + 
                        '&searchInvoiceVO.banId=' + window.GLOBAL_BAN_ID;

    $('.circuit-layer .circuit-info-btns .edit')
        .attr('data-stripped-circuit-number', options.strippedCircuitNumber);

    $.ajax({
        url: 'getMasterInventoryDetails.action',
        type: 'POST',
        data: queryString,
        dataType: 'text',
        success: function(data) {

            // {Object}
            var masterInventoryDetails = JSON.parse(data)[0];

            // Set details count to decide popup element.
            $('.circuit-layer .circuit-info-btns .edit').attr('data-details-count', masterInventoryDetails.detailsCount);

            if ( masterInventoryDetails.detailsCount <= 0 ) {
                $('.circuit-layer .circuit-info-btns .edit').val('Add');
            }

            if (masterInventoryDetails.detailsCount > 0) {

                $('.circuit-layer .circuit-info-table .circuit-status').text(masterInventoryDetails.circuitStatus);
                $('.circuit-layer .circuit-info-table .service-id').text(masterInventoryDetails.serviceId);
                $('.circuit-layer .circuit-info-table .unique-circuit-id').text(masterInventoryDetails.uniqueCircuitId);
                $('.circuit-layer .circuit-info-table .product-category').text(masterInventoryDetails.productCategory);
                $('.circuit-layer .circuit-info-table .installation-date').text(masterInventoryDetails.installationDate);
                $('.circuit-layer .circuit-info-table .disconnection-date').text(masterInventoryDetails.disconnectionDate);

            }

        },

        error: function() {
            throw new Error('Variance get master inventory details failed!');
        }
    });
}

MRCVariance.prototype.initMasterInventoryDetailsListPopupContainerControl = function() {

    YAHOO.ccm.container.varianceCircuitDetails = new YAHOO.widget.Dialog("variance-circuit-details", {
        width: '1100px',
        // fixedcenter: true,
        y: 200,
        visible: false,
        modal: true, // Is modal
        constraintoviewport: true
    });
    
    YAHOO.ccm.container.varianceCircuitDetails.render();
    YAHOO.ccm.container.varianceCircuitDetails.setHeader('Master Inventory');

}

/**
 * If details count > 1, popup master inventory list,
 * else popup master inventory details edit panel.
 * @param  {[type]} elem [description]
 * @return {[type]}      [description]
 */
MRCVariance.prototype.popupEditElement = function(elem) {

    var self = this;
    var detailsCount = parseInt(elem.dataset.detailsCount);
    var strippedCircuitNumber = elem.dataset.strippedCircuitNumber;

    if (detailsCount > 1) {

        self.popupVarianceMasterInventoryDetailsList(strippedCircuitNumber);

    } else {

        self.popupVarianceMasterInventoryDetailsEditor();

    }
    
}

/**
 * Popup variance master inventory details list. 
 */
MRCVariance.prototype.popupVarianceMasterInventoryDetailsList = function(strippedCircuitNumber) {

    var self = this;

    self.renderVarianceMasterInventoryDetailsList(strippedCircuitNumber);

}

MRCVariance.prototype.renderVarianceMasterInventoryDetailsList = function(strippedCircuitNumber) {

    var self = this;

    window.varianceMasterInventoryDetailsListPage = new SANINCO.Page(
        'variance-master-inventory-details-list',
        'varianceMasterInventoryDetailsListPage', 
        {

            vo : "searchInventoryDashboardVO",
            recordText : '',
            totalPageUri : "getMasterInventoryListPageNo.action",
            dataUri :"searchMasterInventoryList.action" ,
            paginationDiv : "variance-master-inventory-details-list-pagination",
            recPerArray : [10,20,30,40,50,70,90,100],
            cols : [
                        { 
                            title : "Key",
                            dataField:"id",
                            filtration : {
                                name:"mid",
                                alias:"Key"
                            },
                            sort : "mid"
                        },

                        { 
                            title : "Summary Vendor Name",
                            dataField:"summaryVendorName",
                            filtration : {
                                name:"summary_vendor_name",
                                alias:"Summary Vendor Name"
                            },
                            sort : "summary_vendor_name"
                        },

                        { 
                            title : "BAN",
                            dataField:"banName",
                            filtration : {
                                name:"ban_name",
                                alias:"BAN"
                            },
                            sort : "ban_name"
                        },

                        { 
                            title : "Invoice Number",
                            dataField:"invoiceNumber",
                            filtration : {
                                name:"invoice_number",
                                alias:"Invoice Number"
                            },
                            sort : "invoice_number"
                        },

                        { 
                            title : "Line of Business",
                            dataField:"lineOfBusiness",
                            filtration : {
                                name:"line_of_business",
                                alias:"Line of Business"
                            },
                            sort : "line_of_business"
                        },

                        { 
                            title : "Invoice Date",
                            dataField:"invoiceDate",
                            filtration : {
                                name:"invoice_date",
                                alias:"Invoice Date"
                            },
                            sort : "invoice_date"
                        },

                        { 
                            title : "Stripped Circuit Number",
                            dataField:"strippedCircuitNumber",
                            filtration : {
                                name:"stripped_circuit_number",
                                alias:"Stripped Circuit Number"
                            },
                            sort : "stripped_circuit_number"
                        },

                        { 
                            title : "Service ID",
                            dataField:"serviceId",
                            filtration : {
                                name:"service_id",
                                alias:"Service ID"
                            },
                            sort : "service_id"
                        },

                        { 
                            title : "Circuit Id Status",
                            dataField:"circuitStatus",
                            filtration : {
                                name:"circuit_status",
                                alias:"Circuit Id Status"
                            },
                            sort : "circuit_status"
                        },
                        { 
                            title : "Access Type",
                            dataField:"accessType",
                            filtration : {
                                name:"access_type",
                                alias:"Access Type"
                            },
                            sort : "access_type"
                        },

                        { 
                            title : "Install Date",
                            dataField:"installDate",
                            filtration : {
                                name:"installation_date",
                                alias:"Install Date"
                            },
                            sort : "installation_date"
                        },

                        { 
                            title : "First Invoice Date",
                            dataField:"firstInvoiceDate",
                            filtration : {
                                name:"first_invoice_date",
                                alias:"First Invoice Date"
                            },
                            sort : "first_invoice_date"
                        },

                        { 
                            title : "First Invoice Number",
                            dataField:"firstInvoiceNumber",
                            filtration : {
                                name:"first_invoice_number",
                                alias:"First Invoice Number"
                            },
                            sort : "first_invoice_number"
                        },

                        { 
                            title : "Disconnection Date",
                            dataField:"disconnectionDate",
                            filtration : {
                                name:"disconnection_date",
                                alias:"Disconnection Date"
                            },
                            sort : "disconnection_date"
                        },

                        { 
                            title : "Service Description",
                            dataField:"serviceDescription",
                            filtration : {
                                name:"service_description",
                                alias:"Service Description"
                            },
                            sort : "service_description"
                        },

                        { 
                            title : "Product Category",
                            dataField:"productCategory",
                            filtration : {
                                name:"product_category",
                                alias:"Product Category"
                            },
                            sort : "product_category"
                        },

                        { 
                            title : "Sub Product Category",
                            dataField:"subProductCategory",
                            filtration : {
                                name:"sub_product_category",
                                alias:"Sub Product Category"
                            },
                            sort : "sub_product_category"
                        },

                        { 
                            title : "Project",
                            dataField:"project",
                            filtration : {
                                name:"project",
                                alias:"Project"
                            },
                            sort : "project"
                        },

                        { 
                            title : "Project Category Status",
                            dataField:"projectCategoryStatus",
                            filtration : {
                                name:"project_category_status",
                                alias:"Project Category Status"
                            },
                            sort : "project_category_status"
                        },

                        { 
                            title : "A Address Street Number",
                            dataField:"aStreetNumber",
                            filtration : {
                                name:"a_street_number",
                                alias:"A Address Street Number"
                            },
                            sort : "a_street_number"
                        },

                        { 
                            title : "A Address Street Name",
                            dataField:"aStreetName",
                            filtration : {
                                name:"a_street_name",
                                alias:"A Address Street Name"
                            },
                            sort : "a_street_name"
                        },

                        { 
                            title : "A Address Unit",
                            dataField:"aUnit",
                            filtration : {
                                name:"a_unit",
                                alias:"A Address Unit"
                            },
                            sort : "a_unit"
                        },

                        { 
                            title : "A Address City",
                            dataField:"aCity",
                            filtration : {
                                name:"a_city",
                                alias:"A Address City"
                            },
                            sort : "a_city"
                        },

                        { 
                            title : "A Address Province",
                            dataField:"aProvince",
                            filtration : {
                                name:"a_province",
                                alias:"A Address Province"
                            },
                            sort : "a_province"
                        },

                        { 
                            title : "A Address Postal Code",
                            dataField:"aPostalCode",
                            filtration : {
                                name:"a_postal_code",
                                alias:"A Address Postal Code"
                            },
                            sort : "a_postal_code"
                        },

                        { 
                            title : "Aggregator CircuitID",
                            dataField:"aggregatorCid",
                            filtration : {
                                name:"aggregator_cid",
                                alias:"Aggregator CircuitID"
                            },
                            sort : "aggregator_cid"
                        },

                        { 
                            title : "Customer Billing Account #",
                            dataField:"customerBillingAccount",
                            filtration : {
                                name:"customer_billing_account",
                                alias:"Customer Billing Account #"
                            },
                            sort : "customer_billing_account"
                        },

                        { 
                            title : "Business Segment",
                            dataField:"businessSegment",
                            filtration : {
                                name:"business_segment",
                                alias:"Business Segment"
                            },
                            sort : "business_segment"
                        },

                        { 
                            title : "End User",
                            dataField:"endUser",
                            filtration : {
                                name:"end_user",
                                alias:"End User"
                            },
                            sort : "end_user"
                        },

                        { 
                            title : "SCOA",
                            dataField:"scoa",
                            filtration : {
                                name:"scoa",
                                alias:"SCOA"
                            },
                            sort : "scoa"
                        },

                        { 
                            title : "Owner",
                            dataField:"owner",
                            filtration : {
                                name:"owner",
                                alias:"Owner"
                            },
                            sort : "owner"
                        },

                        { 
                            title : "USOC",
                            dataField:"usoc",
                            filtration : {
                                name:"usoc",
                                alias:"USOC"
                            },
                            sort : "usoc"
                        },

                        { 
                            title : "Intercompany Business Unit",
                            dataField:"intercompanyBusinessUnit",
                            filtration : {
                                name:"intercompany_business_unit",
                                alias:"Intercompany Business Unit"
                            },
                            sort : "intercompany_business_unit"
                        },

                        { 
                            title : "Intercompany Channel",
                            dataField:"intercompanyChannel",
                            filtration : {
                                name:"intercompany_channel",
                                alias:"Intercompany Channel"
                            },
                            sort : "intercompany_channel"
                        },

                        { 
                            title : "Modified Date",
                            dataField:"modifiedTimestamp",
                            filtration : {
                                name:"modified_timestamp",
                                alias:"Modified Date"
                            },
                            sort : "modified_timestamp"
                        },

                        { 
                            title : "Modified User",
                            dataField:"modifiedUser",
                            filtration : {
                                name:"modified_user",
                                alias:"Modified User"
                            },
                            sort : "modified_user"
                        }
                    
            ]
        });
    
    self.varianceMasterInventoryDetailsListPage = window.varianceMasterInventoryDetailsListPage;

    self.varianceMasterInventoryDetailsListPage.voParam = {
        banId: window.GLOBAL_BAN_ID,
        stripCircuitNumber: encodeURIComponent(strippedCircuitNumber)
    };

    self.varianceMasterInventoryDetailsListPage.addTotalSuccessEvent(function() {
        YAHOO.ccm.container.varianceCircuitDetails.show();
        // $('#variance-circuit-details').show();
    });

    self.setVarianceMasterInventoryDetailsListFilter(self.varianceMasterInventoryDetailsListPage);

    self.varianceMasterInventoryDetailsListPage.start();

}

MRCVariance.prototype.setVarianceMasterInventoryDetailsListFilter = function(startListPage) {

    var self = this;

    var filter1 = new SANINCO.Filter();

    filter1.add('mid', 'number');
    filter1.add('vendor_name', 'string');
    filter1.add('summary_vendor_name', 'string');
    filter1.add('ban_name', 'string');
    filter1.add('invoice_number', 'string')
    filter1.add('line_of_business', 'string');
    filter1.add('invoice_date', 'string');
    filter1.add('stripped_circuit_number', 'string');
    filter1.add('unique_circuit_id','string');
    filter1.add('service_id','string');
    filter1.add('service_id_mrr','string');
    filter1.add('revenue_match_date','string');
    filter1.add('circuit_status','string');
    filter1.add('service_id_match_status','string');
    filter1.add('access_type','string');
    filter1.add('installation_date','string');
    filter1.add('first_invoice_date','string');
    filter1.add('first_invoice_number','string');
    filter1.add('order_number','string');
    filter1.add('order_type','string');
    filter1.add('quote_number','string');
    filter1.add('disconnection_date','string');
    filter1.add('validation_source_system','string');
    filter1.add('cost_type','string');
    filter1.add('service_description','string');
    filter1.add('product_category','string');
    filter1.add('sub_product_category','string');
    filter1.add('project','string');
    filter1.add('project_category_status','string');
    filter1.add('a_street_number','string');
    filter1.add('a_street_name','string');
    filter1.add('a_unit','string');
    filter1.add('a_city','string');
    filter1.add('a_province','string');
    filter1.add('a_postal_code','string');
    filter1.add('a_country','string');
    filter1.add('z_street_number','string');
    filter1.add('z_street_name','string');
    filter1.add('z_unit','string');
    filter1.add('z_city','string');
    filter1.add('z_province','string');
    filter1.add('z_postal_code','string');
    filter1.add('z_country','string');
    filter1.add('serving_wire_centre','string');
    filter1.add('aggregator_cid','string');
    filter1.add('time_slot_vlan_assignment','string');
    filter1.add('comments','string');
    filter1.add('trunk_group_clli','string');
    filter1.add('customer_billing_account','string');
    filter1.add('business_segment','string');
    filter1.add('end_user','string');
    filter1.add('scoa','string');
    filter1.add('owner','string');
    filter1.add('owner_email','string');
    filter1.add('last_signoff_date','string');
    filter1.add('usoc','string');
    filter1.add('intercompany_business_unit','string');
    filter1.add('intercompany_channel','string');
    filter1.add('modified_timestamp','string');
    filter1.add('modified_user','string');

    filter1.addEditeEvent( function(){
        startListPage.start();
    });

    startListPage.setFilter(filter1);
}


/**
 * Hide variance circuit details layer and show invoice details.
 * @return {[type]} [description]
 */
MRCVariance.prototype.hideCircuitDetailsLayer = function() {

    $('.circuit-layer').css('display', 'none');

    if ( $('.variance-hidden').is(':hidden') ) {
        $('.variance-hidden').css('display', 'block');
    }
    
}
