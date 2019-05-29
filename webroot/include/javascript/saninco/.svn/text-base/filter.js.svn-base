(function(){
	if(typeof SANINCO != 'object'){ SANINCO={}; } 
	
	// Get the element throughout 'id' attribute.
	var G = function(id){
		return document.getElementById(id);
	};
	
	// Add a DIV element with [html] content to [ele]
	var A = function(ele,html){
		var e = document.createElement('div');
		ele.appendChild(e);
		e.innerHTML = html;
	};
	
	
	var ReplaceStr = function(name,rd,value){
		value = value.replace(/\'/g,"'\'");
		if(rd == 'Contain'){
			value = value.replace(/\_/g,"\\_");
		}
		return value;
	};
	var B = function(name,rd,value,type){
		value = SANINCO.F_ReplaceStr(name,rd,value);
		if(rd == 'Contain'){
			return name+" like '%" + value + "%'";
		}
		if(rd == 'StartWith'){
			return name+" like '" + value + "%'";
		}
		if(type.toLowerCase() == 'number'){
			return name+rd+value;
		}else{
			return name+rd+"'"+value+"'";
		}
	};
	var AH = function(ns,count){
		var html = '<div class="main-yui-pe-content" id="filter_dialog'+ns+'" style="visibility: hidden;">';
		html +=	'<div class="hd">';
		html +=		'<h2>Filter</h2>';
		html +=	'</div>';
		html +=	'<div class="bd">';
		html +=		'<form id="filter_form'+ns+'">';
		html +=			'<table width=100%>';
		html +=				'<tr> ';
		html +=					'<td colspan="2" id="filter_fieldName'+ns+'" style="color: #0099FF;padding-bottom: 3px;">';
		html +=						'filter_fieldName';
		html +=					'</td>';
		html +=				'</tr>';
		html +=				'<tr>';
		html +=					'<td style="width:50%;">';
		html +=						'<span class="select1" style="border:none;width:98%;"><select id="filter_operation_key_'+ns+'_1" style="width:100%;">';
		html +=							'<option value="Contain">Contain</option>';
		html +=							'<option value="StartWith">Start With</option>';
		html +=							'<option value="=">=</option>';
		html +=							'<option value="&gt;">&gt;</option>';
		html +=							'<option value="&lt;">&lt;</option>';
		html +=							'<option value="&gt;=">&gt;=</option>';
		html +=							'<option value="&lt;=">&lt;=</option>';
		html +=							'<option value="!=">!=</option>';
		html +=						'</select></span>';
		html +=					'</td>';
		html +=					'<td style="width:50%;">';
		html +=						'<input type="text" id="filter_operation_value_'+ns+'_1" style="width:98%;padding:1px 0;"/>';
		html +=					'</td>';
		html +=				'</tr>';

		for(var r=2;r<=count;r++){
			html +=				'<tr>';
			html +=					'<td colspan="2">';
			html +=						'<input type="radio" class="noBorderRadioButton" name="filter_radios_'+ns+'_'+r+'" value="and" id="filter_radio_and_'+ns+'_'+r+'" checked="checked"/>And';
			html +=						'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;';
			html +=						'<input type="radio" class="noBorderRadioButton" name="filter_radios_'+ns+'_'+r+'" value="or" id="filter_radio_or_'+ns+'_'+r+'"/>Or';
			html +=					'</td>';
			html +=				'</tr>';
			html +=				'<tr>';
			html +=					'<td style="width:50%;">';
			html +=						'<span class="select1" style="border:none;width:98%;"><select id="filter_operation_key_'+ns+'_'+r+'" style="width:100%;">';
			html +=							'<option value="Contain">Contain</option>';
			html +=							'<option value="StartWith">Start With</option>';
			html +=							'<option value="=">=</option>';
			html +=							'<option value="&gt;">&gt;</option>';
			html +=							'<option value="&lt;">&lt;</option>';
			html +=							'<option value="&gt;=">&gt;=</option>';
			html +=							'<option value="&lt;=">&lt;=</option>';
			html +=							'<option value="!=">!=</option>';
			html +=						'</select></span>';
			html +=					'</td>';
			html +=					'<td style="width:50%;">';
			html +=						'<input type="text" id="filter_operation_value_'+ns+'_'+r+'" style="width:98%;padding:1px 0;"/>';
			html +=					'</td>';
			html +=				'</tr>';
		}

		html +=			'</table>';
		html +=		'</form>';
		html +=	'</div>';
		html += '</div>';
		// 向文档中添加 filter 骨架结构。
		SANINCO.F_A(document.body,html); 
	};
	
	SANINCO.F_G = G;
	SANINCO.F_A = A;
	SANINCO.F_B = B;
	SANINCO.F_AH = AH;
	SANINCO.F_ReplaceStr = ReplaceStr;
	SANINCO.F_INDEX = 0;
	
	var F = function(count){
		var ns = SANINCO.F_INDEX++;
		this.nameSpace = ns;
		this.count = (count ? count : 2);
		SANINCO.F_AH(ns,this.count);
		this.EDITE_E = [];
		this.addEditeEvent = function(fun){
			this.EDITE_E.push(fun);
		};
		this.items = {}; 
		this.fullImg = 'include/javascript/saninco/img/full.gif';
		this.emptyImg = 'include/javascript/saninco/img/empty.png';
		
		this.editer = new YAHOO.widget.Dialog("filter_dialog"+ns,{
			width : "270px",
		    fixedcenter : true,
		    visible : false, 
			modal: true,
		    constraintoviewport : true,
		    /**
		     * @description  操作按钮
		     * @type {Array}
		     */
		    buttons : [{
				text:"Search", 
				handler:function(){
		    		
					var TO = this.TO;
					/** 命名空间 */
					var ns = TO.nameSpace;
					/** 过滤条件的数量 */
					var count = TO.count;
					/**
					 * @description 获取当前过滤条件所代表的对象结构
					 * @property {Array} TO.items[] 包含当前表格所有过滤字段的数组
					 * @property {String} TO.current 当前需要过滤字段的字符串表现形式
					 */
					var I = TO.items[TO.current];
					/** 用来存储过滤条件的一个对象 */
					I.i1 = {};
					var I1 = I.i1;
					
					/** 目标过滤字段的过滤条件 */
					I.condition = TO.composeFieldFilterConditions(I, I1, ns, TO.count);
					this.cancel();
					if(I.call)I.call();
					for(var b=0;b<TO.EDITE_E.length;b++){
						TO.EDITE_E[b](1);
					}
				}
			},{
				text:"Clear",
				handler:function(){
					var TO = this.TO;
					var ns = TO.nameSpace;
					var count = TO.count;
					var I = TO.items[TO.current];
					I.i1 = {};
					I.condition = "";
					var I1 = I.i1;
					var begin = true;
					for(var r=1;r<=TO.count;r++){
						SANINCO.F_G('filter_operation_key_'+ns+'_'+r).selectedIndex = 0;
						SANINCO.F_G('filter_operation_value_'+ns+'_'+r).value = "";
						I1['opKey'+r] = "";
						I1['opValue'+r] = "";
						if(r!=1){
							SANINCO.F_G('filter_radio_and_'+ns+'_'+r).checked = true;
							I1['rd'+r] = " and ";
						}
					}
				}
			},{
				text:"Cancel",
				handler:function(){
					this.cancel();
				}
			}]
		});
		this.editer.render();
		this.editer.TO = this;
		
		/**
		 * Add elements for filter dialog.
		 * @param { String } Field name.
		 * @param { String } Field type.
		 * @param { Function } call-back.
		 * */
		this.add = function(name,type,call){
			
			// Declare a set.
			var I = this.items[name] = {};
			var self = this;
			
			// Add Properties to the set.
			I.name = name;
			I.type = type;
			I.call = call;
			I.condition = "";
			I.i1 = {};
			
			I.editer = function(name,alias,TO){
				TO.current = name;
				SANINCO.F_G('filter_fieldName'+ns).innerHTML = "<b>"+(alias ? alias : name)+"</b>";		
				var I1 = TO.items[name].i1;
				/** 循环回填之前的过滤条件 */
				for(var r=1;r<=TO.count;r++){
					var rd = ' and ';
					var key = I1['opKey'+r];
					var value = I1['opValue'+r];
					if(r!=1){
						rd = I1['rd'+r];
					}

					// 添加 keydown 事件监听机制
					// 用于监听 Enter 按键。** 输入框
					SANINCO.F_G('filter_operation_value_'+ns+'_'+r)
						.addEventListener('keydown', function(e) {
							/** 阻止冒泡事件 */
							e.stopPropagation();
							if (e.keyCode === 13) {
								
								/**
								 * @description 获取当前过滤条件所代表的对象结构
								 * @property {Array} TO.items[] 包含当前表格所有过滤字段的数组
								 * @property {String} TO.current 当前需要过滤字段的字符串表现形式
								 */
								var I = TO.items[TO.current];
								/** 用来存储过滤条件的一个对象,
								方便记录上一次的查询条件 */
								I.i1 = {};
								var I1 = I.i1;
								
								/** 目标过滤字段的过滤条件 */
								I.condition = TO.composeFieldFilterConditions(I, I1, ns, TO.count);
								TO.editer.cancel();
								if(I.call)I.call();
								/** 内部调用查询列表方法。 */
								for(var b=0;b<TO.EDITE_E.length;b++){
									TO.EDITE_E[b](1);
								}
							}
							
						});
					
					// 添加 keydown 事件监听机制
					// 用于监听 Enter 按键。** 下拉列表
					SANINCO.F_G('filter_operation_key_'+ns+'_'+r)
						.addEventListener('keydown', function(e) {
							/** 阻止冒泡事件 */
							e.stopPropagation();
							/** 阻止下拉列表默认事件 */
							e.target.blur();
							
							if (e.keyCode === 13) {
								
								/**
								 * @description 获取当前过滤条件所代表的对象结构
								 * @property {Array} TO.items[] 包含当前表格所有过滤字段的数组
								 * @property {String} TO.current 当前需要过滤字段的字符串表现形式
								 */
								var I = TO.items[TO.current];
								/** 用来存储过滤条件的一个对象,
								方便记录上一次的查询条件 */
								I.i1 = {};
								var I1 = I.i1;
								
								/** 目标过滤字段的过滤条件 */
								I.condition = TO.composeFieldFilterConditions(I, I1, ns, TO.count);
								TO.editer.cancel();
								if(I.call)I.call();
								/** 内部调用查询列表方法。 */
								for(var b=0;b<TO.EDITE_E.length;b++){
									TO.EDITE_E[b](1);
								}
							}
							
						});
					
					
					
					if(typeof value == 'undefined')value = '';
					if(value!=''){
						var ops = SANINCO.F_G('filter_operation_key_'+ns+'_'+r).options;
						for(var m=0;m<ops.length;m++){
							if(ops[m].value == key){
								ops[m].selected = true;
							}
						}
						SANINCO.F_G('filter_operation_value_'+ns+'_'+r).value = value;
						
						if(rd != ' and '){
							if(r!=1)SANINCO.F_G('filter_radio_or_'+ns+'_'+r).checked = true;
						}else{
							if(r!=1)SANINCO.F_G('filter_radio_and_'+ns+'_'+r).checked = true;
						}
					}else{
						SANINCO.F_G('filter_operation_key_'+ns+'_'+r).selectedIndex = 0;
						SANINCO.F_G('filter_operation_value_'+ns+'_'+r).value = "";
						if(r!=1)SANINCO.F_G('filter_radio_and_'+ns+'_'+r).checked = true;
					}
				}

				TO.editer.show();


			};
		};

		/**
		 * @desc 组合目标过滤字段的过滤条件
		 * @param {Object} [currentFilterableFieldObj] [当前过滤字段的对象表现形式]
		 * @param {Object} [filterableConditionsObj] [用来存储 field 过滤条件的对象]
		 * @param {Number} [namespaceNumber] [当前页面的所有 field filter 组件的命名空间]
		 * @param {Number} [countOfFilterCondition] [当前 field 的过滤条件的数量]
		 * @return {String} [str] 最后拼接查来的条件。
		 */
		this.composeFieldFilterConditions = function (currentFilterableFieldObj, filterableConditionsObj, 
			namespaceNumber, countOfFilterCondition ) {

			var str = "(1=1";
			var begin = true;
			/** 循环过滤条件的个数，之后拼接*/
			for (var r = 1; r <= countOfFilterCondition; r++){
				var rd = ' and ';
				var key = SANINCO.F_G('filter_operation_key_'+namespaceNumber+'_'+r).value;
				var value = SANINCO.F_G('filter_operation_value_'+namespaceNumber+'_'+r).value;
				
				filterableConditionsObj['opKey'+r] = key;
				filterableConditionsObj['opValue'+r] = value;
				if(r!=1){
					rd = SANINCO.F_G('filter_radio_and_'+namespaceNumber+'_'+r).checked ? ' and ' : ' or ';
					filterableConditionsObj['rd'+r] = rd;
				}
				if (value != "") {
					str += (rd + SANINCO.F_B(currentFilterableFieldObj.name, key, value, currentFilterableFieldObj.type));
				}
			}
			str += ")";
			/** 如果条件拼接成的字符串的长度小于7， 则为
			空条件 */
			if(str.length <7) str = "";

			return str;
		}
		
		this.getImg = function(name){
			if(!this.items[name]){
				return this.emptyImg;
			}else if(this.items[name].condition){
				return this.fullImg;
			}else{
				return this.emptyImg;
			}
		};
		
		this.edite = function(name,alias){
			if(!this.items[name]){
				alert("edite name error");
				return false;
			}
			this.items[name].editer(name,alias,this);
		};
		
		this.clearAll = function(){
			for(var name in this.items){
				var I = this.items[name];
				I.condition = "";
				I.i1 = {};
			}
		};
		
		this.getCause = function(){
			var str = "";
			var rd = "";
			for(var j in this.items){
				var I = this.items[j];
				if(I.condition != ""){
					str += rd + I.condition;
					rd = " and ";
				}
			}
			return ccmEscape(str);
		};
	};
	SANINCO.Filter=F;
})();