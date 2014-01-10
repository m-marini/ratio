/*
ratio.js
 */
var result = new Array();
var selectedId;

function defsToJSON() {
	var defs = new Array();
	for (var i = 0; i < result.length; ++i) {
		var o = {
			id : result[i].id,
			text : result[i].text
		};
		defs[i] = o;
	}
	return JSON.stringify(defs);
}

function processDefs() {
	$.getJSON("ratio", {
		defs : defsToJSON()
	}, function(r, stat) {
		if (stat = "success")
			processDefsResponse(r);
	});
}

function processDefsResponse(r) {
	var defs = new Array();
	for (var i = 0; i < result.length; ++i) {
		var o = {
			id : result[i].id,
			text : result[i].text
		};
		defs[i] = o;
	}
	r.indexOf = function(f) {
		for (var i = 0; i < this.length; i++)
			if (f(this[i]))
				return i;
		return -1;
	};
	r.find = function(f) {
		var i = this.indexOf(f);
		return i >= 0 ? this[i] : null;
	};
	result = r;
	$("#exprList tr").remove();
	for (var i = 0; i < result.length; i++) {
		var exp = result[i];
		var tr = $("<tr></tr>").appendTo("#exprList").data("expr", exp);
		$("<td></td>").appendTo(tr).text(exp.id);
		$("<td></td>").appendTo(tr).text(exp.text);
		$("<td></td>").appendTo(tr).text(exp.value);
		if (exp.id == selectedId)
			tr.addClass("ui-selected");
	}
	rebindAll();
}

function rebindAll() {
	$("#exprList").selectable({
		stop : selectExpr
	});
	selectExpr();
}

function loadSample() {
	result = [ {
		id : "V1",
		text : "1;2;3"
	}, {
		id : "V2",
		text : "2;3;4"
	}, {
		id : "V3",
		text : "1/3;1/6;0"
	}, {
		id : "V4",
		text : "7;12;17"
	}, {
		id : "space3D",
		text : "V1,V2,V3,V4"
	}, {
		id : "n",
		text : "rank space3D"
	}, {
		id : "space2D",
		text : "reduce space3D row (0,n-1)"
	}, {
		id : "base2D",
		text : "bases space3D"
	}, {
		id : "base3D",
		text : "I(3)"
	}, {
		id : "space3Dinbase3D",
		text : "base3D*space3D"
	}, {
		id : "space3Dinbase2D",
		text : "base2D*space2D"
	} ];
	processDefs();
}

function showHelp() {
	$("#helpPane").dialog("open");
}

function selectExpr() {
	var s = $("#exprList .ui-selected");
	if (s.length > 0) {
		var e = s.first().data("expr");
		$("#exprId").val(e.id).attr("disabled", false);
		$("#exprText").val(e.text).attr("disabled", false);
		$("#exprValue").text(e.error == "" ? e.fmtValue : e.error);
		$("#applyButton").button("enable");
		$("#restoreButton").button("enable");
		$("#deleteButton").button("enable");
	} else {
		$("#exprId").val("").attr("disabled", true);
		$("#exprText").val("").attr("disabled", true);
		$("#exprValue").text("");
		$("#applyButton").button("disable");
		$("#restoreButton").button("disable");
		$("#deleteButton").button("disable");
		$("#exprError").hide();
	}
}

function addExpr() {
	var i = 0;
	do {
		selectedId = "exp_" + i;
		++i;
	} while (result.indexOf(function(a) {
		return a.id == selectedId;
	}) >= 0);
	result[result.length] = {
		id : selectedId,
		text : "0"
	};
	processDefs();
}

function changeExpr() {
	var oldId = $("#exprList .ui-selected").first().data("expr").id;
	var e = result.find(function(a) {
		return a.id == oldId;
	});
	e.id = $("#exprId").val();
	e.text = $("#exprText").val();
	selectedId = e.id;
	processDefs();
}

function deleteExpr() {
	var oldId = $("#exprList .ui-selected").first().data("expr").id;
	var i = result.indexOf(function(a) {
		return a.id == oldId;
	});
	result.splice(i, 1);
	selectedId = null;
	processDefs();
}

function importExpr() {
	var f = $("#importForm");
	f.ajaxForm({
		success : function(r) {
			var o = $.parseJSON(r);
			processDefsResponse(o);
			$("#importPane").dialog("close");
		},
		complete : function(response) {
		},
		uploadProgress : function(event, position, total, percentComplete) {
		}
	}).submit();
}

$(function() {
	processDefs();
	$("#importPane").dialog({
		autoOpen : false,
		height : 300,
		width : 350,
		modal : true,
		buttons : {
			"Import" : function() {
				importExpr();
			},
			Cancel :

			function() {
				$(this).dialog("close");
			}
		}
	});
	$("#exportButton").button({
		icons : {
			primary : " ui-icon-arrowthick-1-s"
		}
	}).click(
			function(e) {
				e.preventDefault();
				window.location.href = "ratio?cmd=export&defs="
						+ encodeURIComponent(defsToJSON());
			});
	$("#importButton").button({
		icons : {
			primary : " ui-icon-arrowthick-1-n"
		}
	}).click(function() {
		$("#importPane").dialog("open");
	});
	$("#newButton").button({
		icons : {
			primary : "ui-icon-document"
		}
	}).click(function() {
		result = new Array();
		selectedId = null;
		processDefs();
	});
	$("#addButton").button({
		icons : {
			primary : "ui-icon-plus"
		}
	}).click(addExpr);
	$("#deleteButton").button({
		icons : {
			primary : "ui-icon-trash"
		}
	}).click(deleteExpr).button("disable");
	$("#restoreButton").button({
		icons : {
			primary : "ui-icon-refresh"
		}
	}).click(selectExpr).button("disable");
	$("#applyButton").button({
		icons : {
			primary : "ui-icon-check"
		}
	}).click(changeExpr).button("disable");
	$("#sampleButton").button().click(loadSample);
	$("#helpButton").button().click(showHelp);
	$("#helpPane").dialog({
		autoOpen : false,
		height : 400,
		width : 600,
		modal : true
	});
});
