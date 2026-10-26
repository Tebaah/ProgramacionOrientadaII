/**
 * Módulo de la calculadora de voltaje (Ley de Ohm: V = I * R).
 *
 * Requiere java.desktop por el uso de Swing y AWT.
 * Exporta el paquete CalculadoraVoltaje para que la clase principal
 * pueda ser lanzada por el sistema de módulos.
 */
module CalculadoraVoltaje {
    requires transitive java.desktop;
    exports CalculadoraVoltaje;
}
