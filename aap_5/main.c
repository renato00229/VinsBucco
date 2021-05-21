#include <stdio.h>

void  CopiaDaPrimoNumero(unsigned char * stringa1, unsigned char * stringa2);

void main() {

   char str1[]="ciao il 7 luglio ho esami";
	 printf("stringa1 = %s\n", str1);
	 char str2[100];
	 CopiaDaPrimoNumero(str1, str2);
	 printf("stringa2 = %s\n",str2);
}