<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>

<nav class="navbar">
  <ul class="nav nav-tabs">
    <li role="presentation">
      <s:url var="history" action="history">
        <s:param name="bikeId" value="%{bikeId}"/>
      </s:url>
      <s:a href="%{history}"><s:text name="bike.bikeHistory"/></s:a>
    </li>
    <li role="presentation">
      <s:url var="recommendations" action="recommendations">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{recommendations}">
        <s:text name="bike.recommendations"/>
      </s:a>
    </li>
    <li role="presentation" class="active">
      <s:url var="event" action="new-bike-event">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{event}">
        <s:text name="bike.registerNewEvent"/>
      </s:a>
    </li>
    <li role="presentation">
      <s:url var="attachments" action="attachments">
        <s:param name="bikeId" value="bikeId"/>
      </s:url>
      <s:a value="%{attachments}">
        <s:text name="bike.attachments"/>
      </s:a>
    </li>
  </ul>
</nav>

<div class="row">
  <div class="col-md-12">
    <s:form action="update-bike-event" id="bike-event-form" method="POST" cssClass="form-horizontal">
      <s:hidden name="bikeId"/>
      <s:hidden name="bikeEventId"/>
      <s:hidden name="currentMileage"/>

      <div class="row">
        <div class="col-md-6">
          <s:textfield id="event-types"
                       name="eventTypeIds"
                       key="bike.eventTypes"
                       placeholder="%{getText('bike.eventTypes.placeholder')}"
                       tooltip="%{getText('bike.eventTypes.tooltip')}"
                       labelCssClass="col-md-4"
                       elementCssClass="col-md-8"
                       cssClass="input-md"/>
        </div>
        <div class="col-md-2">
          <s:a class="media-middle center-block new-request" action="request-event-type" target="_blank">
            <s:text name="general.requestNew"/>
            <span class="glyphicon glyphicon-new-window"></span>
          </s:a>
        </div>
      </div>

      <s:textarea name="description"
                  key="bikeEvent.description"
                  placeholder="%{getText('bikeEvent.description.placeholder')}"
                  rows="6"
                  tooltip="%{getText('bikeEvent.description.tooltip')}"
                  labelCssClass="col-md-2"
                  elementCssClass="col-md-6"/>

      <sj:datepicker name="registerDate"
                     type="date"
                     key="bike.date"
                     displayFormat="%{userDatePickerFormat}"
                     placeholder="%{getText('bike.date.placeholder')}"
                     parentTheme="bootstrap"
                     labelCssClass="col-md-2"
                     elementCssClass="col-md-3"
                     cssClass="form-control"
                     showOn="focus"
                     inputAppendIcon="calendar"/>

      <s:textfield name="mileage"
                   type="number"
                   key="bike.mileageInKm"
                   placeholder="%{getText('bike.mileageInKm.placeholder')}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-3"
                   cssClass="input-md"
                   helpText="%{getCurrentMileageHelp()}"/>

      <s:textfield name="mth"
                   type="number"
                   key="bike.mth"
                   placeholder="%{getText('bike.mth.placeholder')}"
                   labelCssClass="col-md-2"
                   elementCssClass="col-md-3"
                   cssClass="input-md"
                   helpText="%{currentMthHelp}"/>

      <div class="form-group">
        <div class="col-sm-offset-2 col-md-9">
          <s:submit cssClass="btn btn-primary" key="general.save"/>
        </div>
      </div>
    </s:form>
  </div>
</div>

<script type="application/javascript">

  var $eventTypes = $('#event-types');
  var preloadedItems = $eventTypes.val().split(',');
  $eventTypes.val('');

  $eventTypes.selectize({
    valueField: 'id',
    labelField: 'name',
    searchField: 'name',
    sortField: 'name',
    create: false,
    preload: true,
    render: {
      option: function(item, escape) {
        return '<div><span class="name">' + escape(item.name) + '</span></div>';
      }
    },
    load: function(query, callback) {
      $.ajax({
        url: 'event-types',
        type: 'GET',
        error: function() {
          callback();
        },
        success: function(res) {
          callback(res);
        }
      });
    },
    onLoad: function(_) {
      var selectize = $eventTypes[0].selectize;
      selectize.setValue(preloadedItems);
    }
  });

</script>