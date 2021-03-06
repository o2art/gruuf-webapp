<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<table class="table table-striped">
  <thead>
  <tr>
    <th><s:text name="biker.email.address"/></th>
    <th><s:text name="biker.fullName"/></th>
    <th><s:text name="biker.tokens"/></th>
    <th><s:text name="biker.acceptedPolicies"/></th>
    <th><s:text name="biker.language"/></th>
    <th><s:text name="biker.notify"/></th>
    <th><s:text name="general.timestamp"/></th>
    <th><s:text name="general.actions"/></th>
  </tr>
  </thead>
  <tbody>
  <s:iterator value="list" var="user">
    <tr>
      <td><s:property value="email"/></td>
      <td><s:property value="fullName"/></td>
      <td><s:property value="tokens"/></td>
      <td><s:property value="acceptedPolicies"/></td>
      <td><s:property value="userLocale"/></td>
      <td><s:property value="notify"/></td>
      <td><s:date name="timestamp" format="%{userDateFormat}"/></td>
      <td>
        <s:url var="editUserUrl" action="edit-user">
          <s:param name="userId" value="id"/>
        </s:url>
        <s:a value="%{editUserUrl}"><s:text name="general.edit"/></s:a>
        |
        <s:url var="passwordResetUrl" action="password-reset">
          <s:param name="userId" value="id"/>
        </s:url>
        <s:a value="%{passwordResetUrl}"><s:text name="biker.resetPassword"/></s:a>
      </td>
    </tr>
  </s:iterator>
  </tbody>
</table>