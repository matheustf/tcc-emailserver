package com.puc.tcc.emailserver.enums;

import com.puc.tcc.emailserver.dto.Email;

interface TipoDeEmailInterface {

	Email execute(Email email);
}