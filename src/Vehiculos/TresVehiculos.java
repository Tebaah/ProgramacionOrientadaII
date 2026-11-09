package Vehiculos;

public class TresVehiculos {

    public static void main(String[] args) {
        // Se crean tres instancias de la clase Vehiculo, cada una es un hilo independiente
        Vehiculo vehiculo1 = new Vehiculo(1);
        Vehiculo vehiculo2 = new Vehiculo(2);
        Vehiculo vehiculo3 = new Vehiculo(3);

        // Se inician los tres hilos
        vehiculo1.start();
        vehiculo2.start();
        vehiculo3.start();
    }
}
