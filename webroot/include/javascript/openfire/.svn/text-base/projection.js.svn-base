var projection = {

	/**
	 * 插入比赛投影
	 * params.competitionId : 比赛ID
	 * params.projectionName : 比赛名字
	 * callback : 回调函数
	 */
    insertProjection : function(params, callback) {
    	var url = ContextPath + "/projection.do?method=insertProjection.security";
    	
    	jQuery.post(url, params, function(result){
    		if (callback != null) {
		        callback(result);
    		}
		},"text");
    },
    
    /**
	 * 更新投影激活时间
	 * params.competitionId : 比赛ID
	 * callback : 回调函数
	 */
    updateProjectionActiveTimestamp : function(params, callback) {
    	var url = ContextPath + "/projection.do?method=updateProjectionActiveTimestamp.security";
    	var intervalTime = 5*60*1000;	// 5分钟
    	setInterval(function () {
	    		jQuery.post(url,params, function(result){
		        if (callback != null) {
			        callback(result);
	    		}
			},"text");
    	},intervalTime);
    	
    }
}