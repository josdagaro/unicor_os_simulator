# Unicor OS Simulator
Simulador de gestión de procesos (SO multiprogramado).

Aplicación de escritorio desarrollada en el lenguaje de programación Java utilizando Eclipse.
Se simula la gestión de procesos bajo un modelo de 4 estados los cuales son: Listo, ejecución, terminado y detenido.

En esta simulación, la CPU atiende distintos procesos en el cual cada uno contiene una actividad cuya tarea es realizar un copiado (desde un archivo fuente) y pegado (hacia un archivo destino). Para dicho procesamiento se tiene en cuenta el quantum, tiempo de ráfaga, señales de parada, señales de ejecución, velocidad de copiado, entre otros.

Como bien se menciona, la finalidad es simular la gestión de procesos que realiza un SO multiprogramado, en el cual la CPU es capaz de detener cualquier proceso en un determinado instante y atender otro en tanto que el otro está detenido.
