<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="es_MX"/>
<section id="clientes">
    <div class="container">
        <div class="row">
            <div class="col-md-9">
                <div class="card">
                    <div class="card-header">
                        <h4>Listado de clientes
                        </h4>
                    </div>
                    <table class="table table-stripped">
                        <thead class="thead-dark">
                            <tr>
                                <th>#</th>
                                <th>Nombre</th>
                                <th>Email</th>
                                <th>Telefono</th>
                                <th>Saldo</th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="cliente" items="${clientes}">
                                <tr>
                                    <td>${cliente.idCliente}</td>
                                    <td>${cliente.nombre} ${cliente.apellido}</td>
                                    <td>${cliente.email}</td>
                                    <td>${cliente.telefono}</td>
                                    <td><fmt:formatNumber value="${cliente.saldo}" type="currency"/></td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/servletControlador?accion=editar&idCliente=${cliente.idCliente}"
                                           class="btn btn-secondary">
                                            <i class="fas fa-angle-double-right"></i> Editar
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            <!-- Inicio totales-->
            <div class="col-md-3">
                <div class="card text-white mb-3 bg-danger text-center">
                    <div class="card-body">
                        <h3>Saldo total</h3>
                        <h4 class="display-4">
                             <fmt:formatNumber value="${saldoTotal}" type="currency"/>
                        </h4>
                    </div>
                </div>
                <div class="card text-center bg-success text-white mb-3">
                    <div class="card-body">
                        <h3>Total clientes</h3>
                        <h4 class="display-4">
                            <i class="fas fa-users"></i> ${totalClientes}
                        </h4>
                    </div>
                </div>
            </div>
            <!-- Fin totales-->
        </div>
    </div>
</section>
                        
<!--Agregamos nuestro archivo modal-->
<jsp:include page="/WEB-INF/paginas/clientes/agregarCliente.jsp"/>