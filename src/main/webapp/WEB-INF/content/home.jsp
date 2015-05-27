<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:text name="home.yourBike"/>
<s:iterator value="motorbikes" var="motorbike" >
  <s:url var="url" action="motorbike">
    <s:param name="motorbike">${motorbike.id}</s:param>
  </s:url>
  <s:a href="%{url}">${motorbike.name}</s:a>
</s:iterator>
