package Clases;

import Exepciones.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Jugador {
    private String nombre;
    private String nickname;
    private double sueldo;

    public enum Rol { Support, AWSPer, IGL, Lurker, Rifler, Entry_Fragger }
    private Rol rol;

    public Jugador(String nombre, String nickname, double sueldo, Rol rol) throws SueldoNoValido, NicknameNoExiste, NombreEquipoNoValido {
        setNombre(nombre);
        setNickname(nickname);
        setSueldo(sueldo);
        this.rol = rol;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) throws NombreEquipoNoValido {
        Pattern patronNombre = Pattern.compile("^[A-ZÁÉÍÓÚÜÑ][a-záéíóúüñ]+$");
        Matcher mNombre = patronNombre.matcher(nombre);
        if (!mNombre.matches()) throw new NombreEquipoNoValido();
        this.nombre = nombre;
    }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) throws NicknameNoExiste {
        Pattern patronNick = Pattern.compile("^[A-Za-z0-9_!@#$-]+$");
        Matcher mNick = patronNick.matcher(nickname);
        if (!mNick.matches()) throw new NicknameNoExiste();
        this.nickname = nickname;
    }

    public double getSueldo() { return sueldo; }
    public void setSueldo(double sueldo) throws SueldoNoValido {
        double smi = 16576.0;
        if (sueldo < smi) throw new SueldoNoValido();
        this.sueldo = sueldo;
    }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    @Override
    public String toString() {
        return "Jugador: " + nombre + " (" + nickname + ") - " + rol + " - " + sueldo + "€";
    }
}