function photography_ajaxsubmit(form_id,succes_msg){
	var options = { 
        type: "post",
        dataType: "json",
        url: $('#' + form_id).attr('action'),
        success: function(msg){
        	if(msg == '1')
        		layer.alert(succes_msg, 1, '信息');
        	else
        		layer.alert(msg, 1, '信息');
        }
     };
	 
	$('#' + form_id).ajaxSubmit(options);
}

function getObjectURL(file) {
    var url = null ; 
    if (window.createObjectURL!=undefined) { // basic
        url = window.createObjectURL(file) ;
    } else if (window.URL!=undefined) { // mozilla(firefox)
        url = window.URL.createObjectURL(file) ;
    } else if (window.webkitURL!=undefined) { // webkit or chrome
        url = window.webkitURL.createObjectURL(file) ;
    }
    return url;
}