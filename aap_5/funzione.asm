.586
.model flat
.code
_CopiaDaPrimoNumero proc

	push ebp
	mov ebp, esp

	;stack frame
	;&str1: ebp+8, &str2: ebp+12

	mov ecx, dword ptr[ebp + 8]
	mov edx, dword ptr[ebp + 12]

	dec ecx

	ciclo:
	inc ecx
	mov al, byte ptr[ecx]

	cmp al, 0
	je break

	cmp al, '0'
	jb  ciclo
	
	cmp al, '9'
	ja ciclo

	ciclo_int:
	mov byte ptr[edx], al
	inc edx
	inc ecx

	mov al, byte ptr[ecx]
	cmp al, 0
	je break

	jmp ciclo_int

	break:
	mov byte ptr[edx], 0
	pop ebp
	ret

_CopiaDaPrimoNumero endp
end