/*
ratio.js
 */
var ratio = ratio || {};

ratio.url = "ratio";

ratio.init = function() {
	$("#importPane").dialog({
		autoOpen : false,
		height : 300,
		width : 350,
		modal : true,
		buttons : {
			Import : function() {
				ratio.calculator.import("#importForm");
			},
			Cancel : function() {
				$(this).dialog("close");
			}
		}
	});
	$("#exportButton").button({
		icons : {
			primary : " ui-icon-arrowthick-1-s"
		}
	}).click(function(e) {
		e.preventDefault();
		window.location.href = ratio.calculator.getExportUrl();
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
		ratio.calculator.clearAll();
	});
	$("#addButton").button({
		icons : {
			primary : "ui-icon-plus"
		}
	}).click(function() {
		ratio.calculator.addExpr();
	});
	$("#deleteButton").button({
		icons : {
			primary : "ui-icon-trash"
		}
	}).click(function() {
		ratio.calculator.remove(ratio.explorerPane.getSelectedExpr().id);
	});
	$("#restoreButton").button({
		icons : {
			primary : "ui-icon-refresh"
		}
	}).click(ratio.explorerPane.onSelection);
	$("#applyButton").button({
		icons : {
			primary : "ui-icon-check"
		}
	}).click(
			function() {
				ratio.calculator.change(
						ratio.explorerPane.getSelectedExpr().id,
						ratio.detailsPane.getExpr());
			});
	$("#sampleButton").button().click(function() {
		ratio.calculator.loadSample();
	});
	$("#helpButton").button().click(function() {
		$("#helpPane").dialog("open");
	});
	$("#helpPane").dialog({
		autoOpen : false,
		height : 400,
		width : 600,
		modal : true
	});
	// ratio.calculator.clearAll();
};

ratio.detailsPane = {
	showExpr : function(e) {
		$("#exprId").val(e.id).attr("disabled", false);
		$("#exprText").val(e.text).attr("disabled", false);
		$("#exprValue").text(e.error == "" ? e.fmtValue : e.error);
	},
	clearExpr : function() {
		$("#exprId").val("").attr("disabled", true);
		$("#exprText").val("").attr("disabled", true);
		$("#exprValue").text("");
	},
	getExpr : function() {
		return {
			id : $("#exprId").val(),
			text : $("#exprText").val(),
		};
	}
};

ratio.toolBar = {
	changeExprButtons : function(v) {
		$(".exprButton").button(v);
	}
};

ratio.explorerPane = {
	selected : null,
	onSelection : function() {
		var s = $("#exprList .ui-selected");
		if (s.length > 0) {
			this.selected = s.first().data("expr");
			ratio.detailsPane.showExpr(this.selected);
			ratio.toolBar.changeExprButtons("enable");
		} else {
			this.selected = null;
			ratio.detailsPane.clearExpr();
			ratio.toolBar.changeExprButtons("disable");
		}
	},
	getSelectedExpr : function() {
		return $("#exprList .ui-selected").first().data("expr");
	},

	select : function(e) {
		this.selected = e;
	},
	clearSelection : function() {
		this.selected = null;
		$("#exprList tr").remove().removeClass("uiselected");
	},
	refresh : function() {
		$("#exprList").selectable({
			stop : this.onSelection
		});
		this.onSelection();
	},
	showExprs : function(l) {
		$("#exprList tr").remove();
		var selId = this.selected != null ? this.selected.id : null;
		$.each(l, function(i, e) {
			console.log(selId);
			var tr = $("<tr></tr>").appendTo("#exprList").data("expr", e);
			$("<td></td>").appendTo(tr).text(e.id);
			$("<td></td>").appendTo(tr).text(e.text);
			$("<td></td>").appendTo(tr).text(e.value);
			if (e.id == selId)
				tr.addClass("ui-selected");
		});
		this.refresh();
	}
};

ratio.calculator = {
	results : [],
	sample : [ {
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
	} ],
	map : function(f) {
		var m = [];
		$.each(this.results, function(i, r) {
			m[i] = f(r);
		});
		return m;
	},
	getDefs : function() {
		return this.map(function(r) {
			return {
				id : r.id,
				text : r.text
			};
		});
	},
	toJSON : function() {
		return JSON.stringify(this.getDefs());
	},
	compute : function() {
		var c = this;
		$.getJSON(ratio.url, {
			defs : this.toJSON()
		}, function(r, s) {
			c.onResponse(r, s);
		});
		return this;
	},
	onResponse : function(resp, stat) {
		if (stat = "success")
			this.onComputeSuccess(resp);
		else
			this.onComupteError(stat);
	},
	onComputeSuccess : function(r) {
		this.results = r;
		ratio.explorerPane.showExprs(r);
	},
	onComputeError : function(r) {
		alert(r);
	},
	loadSample : function() {
		this.results = this.sample;
		ratio.explorerPane.clearSelection();
		this.compute();
	},
	indexOf : function(id) {
		for (var i = 0, n = this.results.length; i < n; i++)
			if (this.results[i].id == id)
				return i;
		return -1;
	},
	addExpr : function() {
		var i = 0;
		var id = null;
		do {
			id = "exp_" + i;
			++i;
		} while (this.indexOf(id) >= 0);
		var e = {
			id : id,
			text : "0"
		};
		this.results[this.results.length] = e;
		ratio.explorerPane.select(e);
		this.compute();
	},
	change : function(id, e) {
		this.results[this.indexOf(id)] = e;
		ratio.explorerPane.select(e);
		this.compute();
	},
	remove : function(id) {
		var i = this.indexOf(id);
		this.results.splice(i, 1);
		ratio.explorerPane.clearSelection();
		this.compute();
	},
	clearAll : function() {
		ratio.explorerPane.clearSelection();
		this.onComputeSuccess([]);
	},
	getExportUrl : function() {
		return ratio.url + "?cmd=export&defs="
				+ encodeURIComponent(this.toJSON());
	},
	import : function(s) {
		var c = this;
		$(s).ajaxForm({
			dataType : "json",
			success : function(r) {
				$("#importPane").dialog("close");
				c.onComputeSuccess(r);
			},
			error : function(o, stat, msg) {
				alert(msg);
			}
		}).submit();
	}
};

$(function() {
	ratio.init();
});