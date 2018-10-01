package com.puc.tcc.emailserver.enums;

import java.util.HashMap;

import com.puc.tcc.emailserver.component.ConteudoEmailStatusEntrega;
import com.puc.tcc.emailserver.dto.Email;

public enum TipoDeEmail implements TipoDeEmailInterface {

	BOAS_VINDAS_CLIENTE {

		@Override
		public Email execute(Email email) {
			
			email.setTitulo("Seja Bem Vindo(a) " + email.getNomeDoUsuario());
			email.setFileName("boasvindas.html");

			return email;
		}
	},
	ENTREGA {

		@Override
		public Email execute(Email email) {
			
			HashMap<String, String> contentReplace = email.getContentReplace();
			
			String status = contentReplace.get("**status**");
			
			ConteudoEmailStatusEntrega titulo = ConteudoEmailStatusEntrega.valueOf(status);
			
			email.setTitulo(titulo.toString());
			email.setFileName("statusDeEntrega.html");

			return email;
		}
	},
	Multiply {

		@Override
		public Email execute(Email email) {
			
			email.setTitulo("Sua encomenda foi atualizada, veja mais detalhes");
			email.setFileName("boasvindas.html");

			return email;
		}
	};
}