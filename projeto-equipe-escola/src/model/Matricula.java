package model;

import exception.MatriculaInvalidaException;
import java.util.Date;

public class Matricula {

    public static final String STATUS_PENDENTE   = "PENDENTE";
    public static final String STATUS_CONFIRMADA = "CONFIRMADA";
    public static final String STATUS_CANCELADA  = "CANCELADA";

    private static int contadorId = 1;

    private int    id;
    private Date   dataMatricula;
    private String status;
    private double notaFinal;
    private Aluno  aluno;
    private Turma  turma;

    public Matricula(Aluno aluno, Turma turma) throws MatriculaInvalidaException {
        if (aluno == null)
            throw new MatriculaInvalidaException("Aluno não pode ser nulo para criar uma matrícula.");
        if (turma == null)
            throw new MatriculaInvalidaException("Turma não pode ser nula para criar uma matrícula.");

        this.id            = contadorId++;
        this.aluno         = aluno;
        this.turma         = turma;
        this.dataMatricula = new Date();
        this.status        = STATUS_PENDENTE;
        this.notaFinal     = 0.0;
    }


    public void confirmar() throws MatriculaInvalidaException {
        if (STATUS_CANCELADA.equals(status))
            throw new MatriculaInvalidaException(
                    "Não é possível confirmar uma matrícula cancelada. (ID: " + id + ")"
            );
        if (STATUS_CONFIRMADA.equals(status))
            throw new MatriculaInvalidaException(
                    "Matrícula já está confirmada. (ID: " + id + ")"
            );
        this.status = STATUS_CONFIRMADA;
    }

    public void cancelar() throws MatriculaInvalidaException {
        if (STATUS_CANCELADA.equals(status))
            throw new MatriculaInvalidaException(
                    "Matrícula já está cancelada. (ID: " + id + ")"
            );
        this.status = STATUS_CANCELADA;
    }


    public int    getId(){
        return id; }

    public Date   getDataMatricula(){
        return dataMatricula; }

    public String getStatus(){
        return status; }

    public double getNotaFinal(){
        return notaFinal; }

    public void   setNotaFinal(double n){
        this.notaFinal = n; }

    public Aluno  getAluno(){
        return aluno; }

    public Turma  getTurma(){
        return turma; }

    @Override
    public String toString() {
        return "Matrícula[ID: " + id
                + " | Aluno: "  + aluno.getNome()
                + " | Turma: "  + turma.getCodigo()
                + " | Status: " + status
                + " | Nota: "   + notaFinal + "]";
    }
}
