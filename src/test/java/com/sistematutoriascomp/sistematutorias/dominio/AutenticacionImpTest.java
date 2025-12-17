/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.sistematutoriascomp.sistematutorias.dominio;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.sistematutoriascomp.sistematutorias.model.dao.AutenticacionDAO;
import com.sistematutoriascomp.sistematutorias.model.pojo.Tutor;
import com.sistematutoriascomp.sistematutorias.utilidad.Sesion;

public class AutenticacionImpTest {

    @Test
    public void testIniciarSesionExitoso() {
        System.out.println("Prueba: Iniciar Sesión con credenciales correctas (Sin BD)");

        // Datos de prueba
        String usuario = "12345";
        String password = "password123";
        Tutor tutorSimulado = new Tutor();
        tutorSimulado.setNombre("Profesor de Prueba");

        // "MockedStatic" es lo que permite simular clases estáticas.
        // Usamos un try-with-resources para que el mock se cierre automáticamente al terminar.
        try (MockedStatic<AutenticacionDAO> daoMock = Mockito.mockStatic(AutenticacionDAO.class);
             MockedStatic<Sesion> sesionMock = Mockito.mockStatic(Sesion.class)) {

            // 1. ENSEÑAR AL MOCK (Arrange)
            // Le decimos: "Cuando llamen a verificarSesionTutor con estos datos, devuelve mi tutorSimulado"
            daoMock.when(() -> AutenticacionDAO.verificarSesionTutor(usuario, password))
                   .thenReturn(tutorSimulado);

            // 2. EJECUTAR (Act)
            // Ejecutamos tu método real. Él creerá que la BD le respondió.
            boolean resultado = AutenticacionImp.iniciarSesionTutor(usuario, password);

            // 3. VERIFICAR (Assert)
            // Verificamos que devolvió true
            assertTrue(resultado, "El inicio de sesión debería ser exitoso");

            // Verificamos que tu código haya guardado la sesión (que haya llamado a Sesion.setTutorSesion)
            sesionMock.verify(() -> Sesion.setTutorSesion(tutorSimulado));
        }
    }

    @Test
    public void testIniciarSesionFallido() {
        System.out.println("Prueba: Iniciar Sesión con credenciales incorrectas");

        try (MockedStatic<AutenticacionDAO> daoMock = Mockito.mockStatic(AutenticacionDAO.class)) {
            
            // 1. Arrange: Configuramos que devuelva null (usuario no encontrado)
            daoMock.when(() -> AutenticacionDAO.verificarSesionTutor("usuarioMal", "passMal"))
                   .thenReturn(null);

            // 2. Act
            boolean resultado = AutenticacionImp.iniciarSesionTutor("usuarioMal", "passMal");

            // 3. Assert
            assertFalse(resultado, "El inicio de sesión debería fallar");
        }
    }
}
