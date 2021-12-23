<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>

<!-- Modal -->
<div id="myModal" class="modal">
  <!--  content -->
    <div class="modal-content">
        <div class="modal-header">
            <span class="close">&times;</span>
            <h2>Logout</h2>
        </div>
      <div class="modal-body">
          <p>Vuoi davvero uscire?</p>
          <form method="post" action="${pageContext.request.contextPath}/direttoreServlet/logout">
              <button class="modal-button" type="submit">Si</button>
              <button class="modal-button" type="button" id="close">No</button>
          </form><br>
      </div>
      <div class="modal-footer"></div>
    </div>
</div>