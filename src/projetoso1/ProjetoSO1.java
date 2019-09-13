/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetoso1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URL;

/**
 *
 * @author aliso
 */
public class ProjetoSO1 {

    private static String[] mem_inst;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            ProjetoSO1 proj = new ProjetoSO1();
            proj.run(args);

        } catch (Exception e) {
            System.out.println("WTF: " + e);
        }
    }

    public void run(String[] args) throws Exception {
        try {
            URL url = getClass().getResource("entrada.proj");
            int file_size = countLines(url.getPath()); //Conta o numero de linhas do arquivo
            System.out.println("Numero de Linhas: " + file_size + "\n\n");

            FileReader file = new FileReader(url.getPath()); //Localizacao do arquivo
            BufferedReader arq = new BufferedReader(file);

            CPU cpu;
            mem_inst = new String[file_size];
            cpu = new CPU(mem_inst);

            /**
             * Carrega o arquivo na memoria*
             */
            memory_fetch(arq);

            /**
             * CPU executa o programa*
             */
            cpu.run();

            arq.close();
            file.close();

        } catch (FileNotFoundException fnf) {
            System.out.println("Arquivo nao encontrado");
        }
    }

    public static int countLines(String filename) throws IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(filename));
        while (reader.readLine() != null);
        //int cnt = reader.getLineNumber();
        reader.close();
        return reader.getLineNumber();
    }

    public static void memory_fetch(BufferedReader br) throws IOException {
        int i = 0, size = mem_inst.length;
        String line;

        while (i < size && br.ready()) {
            line = br.readLine();
            if (line.equals("")) {
                continue;
            }

            mem_inst[i] = line;
            i++;
        }
    }

    public class CPU {

        private String[] memoria;
        private double retornoMedio;
        private double respostaMedio;
        private double esperaMedio;

        public CPU(String[] mem_inst) {
            this.memoria = mem_inst;

        }

        public void run() {

            FCFS(memoria.clone());
            SJF(memoria.clone());
            RR(memoria.clone());

        }

        public void FCFS(String[] processors) {

            double retorno = 0;
            double resposta = 0;
            double espera = 0;
            double tempFinal = 0;
            double aux = 0;

            //////
  
            for (String processor : processors) {
                String[] tokens = processor.split(" ");
                retorno += ((aux += Double.parseDouble(tokens[1]))-Double.parseDouble(tokens[0]));
                
                //
                //   Tempo transcorrido desde que se lança um processo
                //    (entra na fila de prontos) até que finalize sua execução.
                //

            }
             

            /////
            tempFinal = 0;
            for (int i = 0; i < (processors.length); i++) {
                String tokens[] = processors[i].split(" ");
                tempFinal += Double.parseDouble(tokens[1]);  // Pegar o tempo Final do processo
                resposta += (tempFinal - Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[1])); // Formula

            }
            //////
            tempFinal = 0;
            for (int i = 0; i < (processors.length); i++) {
                String tokens[] = processors[i].split(" ");
                tempFinal += Double.parseDouble(tokens[1]);  // Pegar o tempo Final do processo
                espera += (tempFinal - Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[1])); // Formula
                

            }

            //////
            System.out.println("FCFS: " + (retorno / processors.length) + " " + (resposta / processors.length) + " " + (espera / processors.length));

        }

        public void SJF(String[] processors) {

            double retorno = 0;
            double resposta = 0;
            double espera = 0;
            double aux = 0, tempFinal = 0;

            processors = ordemSort(processors);
            
            for(String proc : processors){
                System.out.println(proc);
            }
            //////
            for (String processor : processors) {
                String[] tokens = processor.split(" ");
                retorno += ((aux += Double.parseDouble(tokens[1]))-Double.parseDouble(tokens[0]));
                
                //
                //   Tempo transcorrido desde que se lança um processo
                //    (entra na fila de prontos) até que finalize sua execução.
                //

            }
             

            /////
            tempFinal = 0;
            for (int i = 0; i < (processors.length); i++) {
                String tokens[] = processors[i].split(" ");
                tempFinal += Double.parseDouble(tokens[1]);  // Pegar o tempo Final do processo
                resposta += (tempFinal - Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[1])); // Formula

            }
            //////
            tempFinal = 0;
            for (int i = 0; i < (processors.length); i++) {
                String tokens[] = processors[i].split(" ");
                tempFinal += Double.parseDouble(tokens[1]);  // Pegar o tempo Final do processo
                espera += (tempFinal - Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[1])); // Formula
                

            }

            //////
            System.out.println("SJF: " + (retorno / processors.length) + " " + (resposta / processors.length) + " " + (espera / processors.length));

        }

        public void RR(String[] processors) {

            double retorno = 0;
            double resposta = 0;
            double espera = 0;
            int quantum = 2;
            boolean work = true;
            int relay = 0;

            processors = executaInc(processors);
            

            
            for (int i = 0; i < (processors.length - 1); i++) {
                String tokens[] = processors[i].split(" ");

                if (Integer.parseInt(tokens[1]) <= quantum) {
                    resposta += Integer.parseInt(tokens[1]);
                }
                if (Integer.parseInt(tokens[1]) > quantum) {
                    resposta += quantum;
                }

            }

            //////
            for (int i = 0; i < (processors.length - 1); i++) {
                String tokens[] = processors[i].split(" ");
                espera += ( Double.parseDouble(tokens[1]));  // Recebe o tempo anterior A = A + A // Calcula o tempo anterior o anterior a ele B = A + B

            }
            
            while (work) {

                work = false;
                for (int i = 0; i < processors.length; i++) {

                    String[] tokens = processors[i].split(" ");

                    if (Integer.parseInt(tokens[1]) < quantum && Integer.parseInt(tokens[1]) != 0) {

                        int progress = Integer.parseInt(tokens[1]) + Integer.parseInt(tokens[2]);
                        processors[i] = tokens[0] + " " + 0 + " " + progress + " ";
                        work = true;
                    }
                    if (Integer.parseInt(tokens[1]) == quantum) {
                        int progress = Integer.parseInt(tokens[1]) + (relay * quantum);
                        processors[i] = tokens[0] + " " + 0 + " " + progress + " ";
                        work = true;
                    }
                    if (Integer.parseInt(tokens[1]) > quantum) {

                        int a = Integer.parseInt(tokens[1]) - quantum,
                                b = (relay * quantum);
                        processors[i] = tokens[0] + " " + a + " " + b + " ";
                        work = true;
                    }
                    if (Integer.parseInt(tokens[1]) != 0) {
                        String[] tokens2 = processors[i].split(" ");
                        /* TESTE */
                        // System.out.println("P"+i+": "+tokens2[0]+" "+tokens2[1]+" "+tokens2[2]);
                        relay++;
                    }

                }

            }
            for (String processor : processors) {
                String[] tokens = processor.split(" ");
                retorno += Double.parseDouble(tokens[2]);
                /*
                    Tempo transcorrido desde que se lança um processo
                    (entra na fila de prontos) até que finalize sua execução.
                 */

            }

            /////
            System.out.println("RR: " + (retorno / processors.length) + " " + (resposta / processors.length) + " " + (espera / processors.length));

        }

        public String[] ordemSort(String vetor[]) {
            for (int i = vetor.length; i >= 1; i--) {
                for (int j = 1; j < i; j++) {
                    String tokens[] = vetor[j - 1].split(" ");
                    String tokens2[] = vetor[j].split(" ");
                    //if (Double.parseDouble(tokens[1]) > Double.parseDouble(tokens2[1])) {
                    if ((Double.parseDouble(tokens[0])+Double.parseDouble(tokens[1])) >= (Double.parseDouble(tokens2[0])+Double.parseDouble(tokens2[1]))) {
                        String aux = vetor[j];
                        vetor[j] = vetor[j - 1];
                        vetor[j - 1] = aux;
                    }
                }
            }
            return vetor;
        }

        public double soma(double v[], int n) {

            if (n == 1) {
                return v[0];
            } else {
                return v[n - 1] + soma(v, n - 1);
            }

        }

        public String[] executaInc(String procS[]) {

            for (int i = 0; i < procS.length; i++) {
                String tokens[] = procS[i].split(" ");
                procS[i] = tokens[0] + " " + tokens[1] + " " + 0 + " ";
            }

            return procS;
        }

    }

}
