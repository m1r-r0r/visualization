//----------------------------------- utils ----------------------------------------//

function showElement(elementId) {
    document.getElementById(elementId).style.display = "block";
}
function hideElement(elementId) {
    document.getElementById(elementId).style.display = "none";
}

//----------------------------------- add filters ----------------------------------------//

function addFilter() {
    var columnFilter = document.getElementById("column-filter");
    var newFilterId = columnFilter.options[columnFilter.selectedIndex].id;
    var filtersBlock = document.getElementById("filtersBlock");
    var filtersId = filtersBlock.children;
    var check=true;
    if(filtersId.length > 1) {
        var ids = filtersId[1].id;
        var i=1;
        while(i < filtersId.length && check) {
            if(filtersId[i].id == newFilterId + "_filter") {
                check=false;
            }
            i++;
        }
    }
    if(check) {
        var newFilter = document.createElement("button");
        filtersBlock.appendChild(newFilter);
        newFilter.setAttribute("id", newFilterId + "_filter");
        newFilter.setAttribute("class", "filter");
        newFilter.innerHTML = "Filter " + newFilterId;
        newFilter.setAttribute("data-min-value", "");
        newFilter.setAttribute("data-max-value", "");
        newFilter.setAttribute("onclick", "changeFilter(this.id)")
        newFilter.setAttribute("data-filter-name", newFilterId);
    }
    hideElement("add-filter");
}

function changeFilter(filterId) {
    showElement('change-filter');
    document.getElementById('change-filter-id-paragraph').innerHTML = filterId;
    var minValue = document.getElementById("min-value");
    var maxValue = document.getElementById("max-value");
    var filterElement = document.getElementById(filterId);
    minValue.value = filterElement.getAttribute("data-min-value");
    maxValue.value = filterElement.getAttribute("data-max-value");
}
function deleteFilter() {
    var filterId = document.getElementById("change-filter-id-paragraph").innerHTML;
    document.getElementById(filterId).remove();
    hideElement('change-filter');
}
function saveFilterChanges() {
    var filterId = document.getElementById("change-filter-id-paragraph").innerHTML;
    var filterElement = document.getElementById(filterId);
    var minValue = document.getElementById("min-value").value;
    var maxValue = document.getElementById("max-value").value;
    filterElement.setAttribute("data-min-value", minValue);
    filterElement.setAttribute("data-max-value", maxValue);
    hideElement('change-filter');
}

//----------------------------------- add fields ----------------------------------------//
function showAddFieldPopup(buttonId) {
    showElement('add-field');
    if(buttonId == "add-measurement-button") typeId = "measurementsBlock";
    else typeId = "measuresBlock";
    document.getElementById("add-field-id-paragraph").setAttribute("data-field-block-id", typeId);
}

function addField() {
    var columnField = document.getElementById("column-field");
    var newFieldId = columnField.options[columnField.selectedIndex].id;
    var typeId = document.getElementById("add-field-id-paragraph").getAttribute("data-field-block-id");
    var fieldsBlock = document.getElementById(typeId);
    var fieldsId = fieldsBlock.children;
    var check=true;
    if(fieldsId.length > 1) {
        var ids = fieldsId[1].id;
        var i=1;
        while(i < fieldsId.length && check) {
            if(fieldsId[i].id == newFieldId + "-" + typeId) {
                check=false;
            }
            i++;
        }
    }
    if(check) {
        var newField = document.createElement("button");
        fieldsBlock.appendChild(newField);
        newField.setAttribute("id", newFieldId + "-" + typeId);
        newField.setAttribute("class", "field-container");
        newField.innerHTML = newFieldId;
        newField.setAttribute("onclick", "changeField(this.id)");
        if(typeId == "measuresBlock") newField.setAttribute("data-lead-field", "0");
        newField.setAttribute("data-field-type", typeId);
        newField.setAttribute("data-field-name", newFieldId);
    }
    hideElement("add-field");
}

function changeField(fieldButtonId) {
    var fieldButton = document.getElementById(fieldButtonId);
    var paragraphElement = document.getElementById("change-field-id-paragraph");
    paragraphElement.innerHTML = fieldButton.getAttribute("data-field-name");
    paragraphElement.setAttribute("data-field-id", fieldButtonId);
    var buttonType = fieldButton.getAttribute("data-field-type");
    paragraphElement.setAttribute("data-field-type", buttonType);
    if(buttonType != "measuresBlock") document.getElementById("label-for-change-field-lead-checkbox").style.display = "none";
    else {
        document.getElementById("label-for-change-field-lead-checkbox").style.display = "block";
        if(fieldButton.getAttribute("data-lead-field") == "1") document.getElementById("change-field-lead-checkbox").checked = true;
        else document.getElementById("change-field-lead-checkbox").checked = false;
    }
    showElement('change-field');
}

function deleteField() {
    var fieldButtonId = document.getElementById("change-field-id-paragraph").getAttribute("data-field-id");
    document.getElementById(fieldButtonId).remove();
    hideElement('change-field');
}

function saveFieldChanges() {
    var fieldButtonId = document.getElementById("change-field-id-paragraph").getAttribute("data-field-id");
    var fieldButton = document.getElementById(fieldButtonId);
    var buttonType = fieldButton.getAttribute("data-field-type");
    if(buttonType == "measuresBlock") {
        var leadFieldCheckbox = document.getElementById("change-field-lead-checkbox").checked;
        if(leadFieldCheckbox) {
            fieldButton.setAttribute("data-lead-field", "1");
            fieldButton.setAttribute("class", "lead-field-container");
            var measuresBlockChildren = document.getElementById("measuresBlock").children;
            for(var i = 1; i < measuresBlockChildren.length; i++) {
                var childId = measuresBlockChildren[i].id;
                if(childId != fieldButtonId) {
                    measuresBlockChildren[i].setAttribute("data-lead-field", "0");
                    measuresBlockChildren[i].setAttribute("class", "field-container");
                }
            }
        } else fieldButton.setAttribute("data-lead-field", "0");

    }
    hideElement('change-field');
}

function gatherParams() {
    var graphTypeSelect = document.getElementById("graph-type-select");
    var measuresBlock = document.getElementById("measuresBlock").children;
    var measurementsBlock = document.getElementById("measurementsBlock").children;
    var filtersBlock = document.getElementById("filtersBlock").children;
    var paramsToPost = document.getElementById("params-to-post");
    var params = "&block=updateBlock";

    params = params + "&el=" + "graph-type-select"
        + "&attr=" + graphTypeSelect.options[graphTypeSelect.selectedIndex].id;

    params = params + "&block=filtersBlock";
    for(var i=1; i<filtersBlock.length; i++) {
        var filterChild = filtersBlock[i];
        params = params
            + "&el=" + filterChild.getAttribute("data-filter-name")
            + "&attr=" + filterChild.getAttribute("id")
            + "&attr=" + filterChild.getAttribute("data-min-value")
            + "&attr=" + filterChild.getAttribute("data-max-value");
    }
    params = params + "&block=measurementsBlock";
    for(var i=1; i<measurementsBlock.length; i++) {
        var measurementChild = measurementsBlock[i];
        params = params
            + "&el=" + measurementChild.getAttribute("data-field-name")
            + "&attr=" + measurementChild.getAttribute("id");
    }
    var categoryStr = "&block=categoryBlock";
    params = params + "&block=measuresBlock";
    for(var i=1; i<measuresBlock.length; i++) {
        var measureChild = measuresBlock[i];
        var leadField = measureChild.getAttribute("data-lead-field");
        if(leadField == "0") {
            params = params
                + "&el=" + measureChild.getAttribute("data-field-name")
                + "&attr=" + measureChild.getAttribute("id");
        } else {
            categoryStr = categoryStr
              + "&el=" + measureChild.getAttribute("data-field-name")
                    + "&attr=" + measureChild.getAttribute("id");
        }
    }
    params = params + categoryStr;

    paramsToPost.value = params;
    document.getElementById("send-post-button").click();
}

function paramsAfterPost() {
    var testDiv = document.getElementById("test");
    var paramsToPost = document.getElementById("params-to-post");
    if(paramsToPost.value != "none") {
        var paramsAfterPostValue = paramsToPost.value;
        var documentBlocks = paramsAfterPostValue.split("&block=");
        var chartTypeSelect = documentBlocks[1].split("&el=")[1].split("&attr=")[1];
            var graphTypeSelect = document.getElementById("graph-type-select");
            for(var i=0; i<graphTypeSelect.length; i++)
                if(graphTypeSelect[i].id == chartTypeSelect) graphTypeSelect[i].selected = true;
        var filtersBlockPost = documentBlocks[2].split("&el=");
            if(filtersBlockPost.length > 1) {
                var filtersBlock = document.getElementById("filtersBlock");
                for(var i=1; i<filtersBlockPost.length; i++) {
                    var filterAttr = filtersBlockPost[i].split("&attr=");
                    var newFilter = document.createElement("button");
                        filtersBlock.appendChild(newFilter);
                        newFilter.setAttribute("id", filterAttr[0] + "_filter");
                        newFilter.setAttribute("class", "filter");
                        newFilter.innerHTML = "Filter " + filterAttr[0];
                        newFilter.setAttribute("data-min-value", filterAttr[2]);
                        newFilter.setAttribute("data-max-value", filterAttr[3]);
                        newFilter.setAttribute("onclick", "changeFilter(this.id)")
                        newFilter.setAttribute("data-filter-name", filterAttr[0]);
                }
            }
        var measurementsBlockPost = documentBlocks[3].split("&el=");
        var measurementsBlock = document.getElementById("measurementsBlock");
            for(var i=1; i<measurementsBlockPost.length; i++) {
                var measurementAttr = measurementsBlockPost[i].split("&attr=");
                var newField = document.createElement("button");
                    measurementsBlock.appendChild(newField);
                    newField.setAttribute("id", measurementAttr[0] + "-" + "measurementsBlock");
                    newField.setAttribute("class", "field-container");
                    newField.innerHTML = measurementAttr[0];
                    newField.setAttribute("onclick", "changeField(this.id)");
                    newField.setAttribute("data-field-type", "measurementsBlock");
                    newField.setAttribute("data-field-name", measurementAttr[0]);
            }
        var measuresBlockPost = documentBlocks[4].split("&el=");
        var measuresBlock = document.getElementById("measuresBlock");
            for(var i=1; i<measuresBlockPost.length; i++) {
                var measureAttr = measuresBlockPost[i].split("&attr=");
                var newField = document.createElement("button");
                    measuresBlock.appendChild(newField);
                    newField.setAttribute("id", measureAttr[0] + "-" + "measuresBlock");
                    newField.setAttribute("class", "field-container");
                    newField.innerHTML = measureAttr[0];
                    newField.setAttribute("onclick", "changeField(this.id)");
                    newField.setAttribute("data-lead-field", "0");
                    newField.setAttribute("data-field-type", "measuresBlock");
                    newField.setAttribute("data-field-name", measureAttr[0]);
            }
        var categoryBlockPost = documentBlocks[5].split("&el=");
        var measuresBlock = document.getElementById("measuresBlock");
            for(var i=1; i<categoryBlockPost.length; i++) {
                var categoryAttr = categoryBlockPost[i].split("&attr=");
                var newField = document.createElement("button");
                    measuresBlock.appendChild(newField);
                    newField.setAttribute("id", categoryAttr[0] + "-" + "measuresBlock");
                    newField.setAttribute("class", "lead-field-container");
                    newField.innerHTML = categoryAttr[0];
                    newField.setAttribute("onclick", "changeField(this.id)");
                    newField.setAttribute("data-lead-field", "1");
                    newField.setAttribute("data-field-type", "measuresBlock");
                    newField.setAttribute("data-field-name", categoryAttr[0]);
            }
    }

}