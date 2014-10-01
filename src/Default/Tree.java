package Default;

import java.util.ArrayList;
import javax.swing.*;
//
/**
 *
 * @author Cristobal
 * @author Cesar
 *
 */
public class Tree {

    private Node root = new Node(-1, null);
    String use = "";
    ArrayList<String> codes = new ArrayList<String>();

    public Tree() {
    }

    /**
     * Ordena un string ingresado
     *
     * @param in
     * @return
     */
    private String orderString(String in) {
        String lc = in;
        char[] arr = new char[lc.length()];

        for (int i = 0; i < lc.length(); i++) {
            arr[i] = lc.charAt(i);
        }

        //Bubble sort extraido de http://www.algolist.net/Algorithms/Sorting/Bubble_sort
        //Inicio Bubble
        boolean swapped = true;
        int j = 0;
        char tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = 0; i < arr.length - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    tmp = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = tmp;
                    swapped = true;
                }
            }
        }
        //Fin Bubble

        //http://stackoverflow.com/questions/14957964/concantenating-elements-in-an-array-to-a-string
        StringBuffer impo = new StringBuffer();
        for (int i = 0; i < arr.length; i++) {
            impo.append(arr[i]);
        }
        String out = impo.toString();
        return out;
    }

    /**
     * Obtiene las repeticiones de las letras
     *
     * @param out
     * @return Node[]
     */
    public Node[] getChance(String out) {
        if (out.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Ha ingresado un string vacio",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("El string ingresado es vacio");
        }
        String modified = orderString(out);
        String cut = "";
        int i = 0;
        while (i < modified.length()) {
            if (modified.length() == 1) {
                cut += modified.charAt(0);
                break;
            }
            if (i < modified.length() - 1) {
                if (modified.charAt(i) != modified.charAt(i + 1)) {
                    cut += modified.charAt(i);
                    i++;
                    continue;
                }
                if (modified.charAt(i) == modified.charAt(i + 1)) {
                    i++;
                    continue;
                }
            }
            if (i == modified.length() - 1) {
                if (modified.charAt(i) == modified.charAt(i - 1)) {
                    cut += modified.charAt(i);
                    i++;
                    continue;
                }
                if (modified.charAt(i) != modified.charAt(i - 1)) {
                    cut += modified.charAt(i);
                    i++;
                    continue;
                }
            }

        }
        if (cut.length() == 1) {
            JOptionPane.showMessageDialog(null,
                    "Ingrese strings con mas de un caracter diferente",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("Imposible crear arbol con solo un caracter");
        }
        Node[] coso = new Node[cut.length()];
        int pos = 0;
        for (int j = 0; j < cut.length(); j++) {
            Node temp = new Node(counter(modified, cut.charAt(j)), charToString(cut.charAt(j)));
            coso[pos] = temp;
            if (pos < cut.length()) {
                pos++;
            }
            System.out.print("cut : " + cut.charAt(j) + ": ");
            System.out.println(counter(modified, cut.charAt(j)));
        }

        return orderNodes(coso);
    }

    private Node[] orderNodes(Node[] arr) {
        boolean swapped = true;
        int j = 0;
        Node tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = arr.length - j; i > 0; i--) {
                if (arr[i].getChance() > arr[i - 1].getChance()) {
                    tmp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = tmp;
                    swapped = true;
                }
            }
        }
        buildTree(arr);
        return arr;
    }

    private Node[] realOrderNodes(Node[] arr) {
        boolean swapped = true;
        int j = 0;
        Node tmp;
        while (swapped) {
            swapped = false;
            j++;
            for (int i = arr.length - j; i > 0; i--) {
                if (arr[i].getChance() > arr[i - 1].getChance()) {
                    tmp = arr[i];
                    arr[i] = arr[i - 1];
                    arr[i - 1] = tmp;
                    swapped = true;
                }
            }
        }
        return arr;
    }

    private void buildTree(Node[] in) {
        int i = in.length - 1;
        while (countElemens(in) > 1 && i > 0) {
            int newChance = in[i].getChance() + in[i - 1].getChance();
            String newString = in[i - 1].getAlpha() + in[i].getAlpha();
            Node fresh = new Node(newChance, newString);
            fresh.setLeft(in[i - 1]);
            fresh.getLeftSon().setFather(fresh);
            fresh.setRight(in[i]);
            fresh.getRightSon().setFather(fresh);
            in[i] = null;
            in[i - 1] = fresh;
            in = diminisharray(in);
            if ((countElemens(in)) == 1) {
                this.root = fresh;
                use = this.root.getAlpha();
            }
            realOrderNodes(in);
            i--;
        }
        root = in[0];
        String a = "";
        codes.clear();
        buildCode(root, a);
        System.out.println(use);
        JOptionPane.showMessageDialog(null, "Arbol Creado");
    }

    //http://algs4.cs.princeton.edu/55compression/Huffman.java.html
    //Algoritmo hecho en la universidad de Princeton, modificado para nosotros.
    private void buildCode(Node x, String s) {
        String code = s;
        if (!x.isLeaf(x)) {
            buildCode(x.getLeftSon(), code + '0');
            buildCode(x.getRightSon(), code + '1');

        } else {
            System.out.println(x.getAlpha() + ":" + code);
            x.setCode(code);
            codes.add(code);
        }

    }

    public void codeString(String in) {
        String vacio = "";
        for (int i = 0; i < in.length(); i++) {
            for (int j = 0; j < use.length(); j++) {
                if (in.charAt(i) == use.charAt(j)) {
                    vacio += codes.get(j);
                }
            }
        }

        if (vacio.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Simbolo no reconocido o arbol no creado",
                    "Cuidado",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, ("El codigo asociado es: "+ "\n" + vacio));

        }
        System.out.println(vacio);
    }

    public void decodeCode(String in) {
        if(in.equals("")){
            JOptionPane.showMessageDialog(null,
                    "Ingrese cadena Huffman valida",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            throw new IllegalArgumentException("No es posible decodificar un Huffman vacio");
        }
        
        String actualcode = "";
        String vacio = "";
        for (int i = 0; i < in.length(); i++) {
            actualcode += in.charAt(i);
            for (int j = 0; j < codes.size(); j++) {
                
                if (actualcode.equals(codes.get(j))) {
                    vacio += use.charAt(j);
                    actualcode = "";
                }
            }
        }
        JOptionPane.showMessageDialog(null, ("La decodificacion asociada es: "+ "\n" + vacio));

        //System.out.println("Not implemented yet");
        //JOptionPane.showMessageDialog(null, "Not implemented yet");

    }

    private int countElemens(Node[] in) {
        int cont = 0;
        for (int i = 0; i < in.length; i++) {
            if (in[i] != null) {
                cont++;
            }
        }
        return cont;
    }

    private String charToString(char in) {
        return "" + in;
    }

    private int counter(String s, char a) {

        int counter = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == a) {
                counter++;
            }
        }
        return counter;
    }

    private Node getRoot() {
        return this.root;
    }

    private boolean included(String in, char patron) {
        boolean isIncluded = false;
        for (int i = 0; i < in.length(); i++) {
            if (in.charAt(i) == patron) {
                isIncluded = true;
            }
        }
        return isIncluded;
    }

    private Node[] diminisharray(Node[] array) {
        Node arreglo[] = new Node[array.length - 1];
        for (int i = 0; i < array.length - 1; i++) {
            arreglo[i] = array[i];
        }
        return arreglo;
    }

}
