package com.ronaldo.curso.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class URL {

	public static List<Integer> decodeIntList(String s) {
		/*String[] vet = s.split(",");
		List<Integer> list = new ArrayList<>();
		for(int i = 0; i <s.length(); i++) {
			list.add(Integer.parseInt(vet[i]));
		}*/
		/**
		 * Quebra a lista com base na virgula
		 * converte o caracter em inteiro
		 * adiciona na lista e retorna
		 */
		return Arrays.asList(s.split(",")).stream().map(x -> Integer.parseInt(x)).collect(Collectors.toList());
	}
	/**
	 * Tirar o encode dos parametros da URI
	 * @param s
	 * @return
	 */
	public static String decodeParam(String s) {
		try {
			return URLDecoder.decode(s, "UTF-8");			
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
}