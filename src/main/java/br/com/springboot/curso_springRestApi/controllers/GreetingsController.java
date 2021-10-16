package br.com.springboot.curso_springRestApi.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.tools.DocumentationTool.Location;

import org.springframework.asm.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.springboot.curso_springRestApi.model.Usuario;
import br.com.springboot.curso_springRestApi.repository.UsuarioRepository;

/**
 *
 * A sample greetings controller to return greeting text
 */
@RestController
public class GreetingsController {

	@Autowired // IC/CD ou CDI - Injeção de dependencia
	private UsuarioRepository usuarioRepository;

	/**
	 *
	 * @param name the name to greet
	 * @return greeting text
	 */

	@RequestMapping(value = "/mostrarnome/{name}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String greetingText(@PathVariable String name) {
		return "Bem Vindo " + name + "!";
	}

	@RequestMapping(value = "/olamundo/{nome}", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	public String retornaOlaMundo(@PathVariable String nome) {

		Usuario usuario = new Usuario();

		usuario.setNome(nome);

		usuarioRepository.save(usuario);// grava no banco de dados

		return "Olá mundo " + nome;
	}

	@GetMapping(value = "listatodos") // nosso primeiro metodo de API
	@ResponseBody // retorna os dados para o corpo da resposta
	public ResponseEntity<List<Usuario>> listaUsuario() {

		List<Usuario> usuarios = usuarioRepository.findAll(); // executa a consulta no banco de dados

		return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK); // retorna a lista em JSON

	}

	@PostMapping(value = "salvar") // mapeia a url
	@ResponseBody // descricao da resposta
	public ResponseEntity<Usuario> salvar(@RequestBody Usuario usuario) { // recebe os dados para salvar

		Usuario user = usuarioRepository.save(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.CREATED);
	}

	@PostMapping(value = "atualizar") // mapeia a url
	@ResponseBody // descricao da resposta
	public ResponseEntity<?> atualizar(@RequestBody Usuario usuario) { // recebe os dados para salvar

		if (usuario.getId() == null) {
			return new ResponseEntity<String>("Id não foi informado", HttpStatus.OK);
		}

		Usuario user = usuarioRepository.saveAndFlush(usuario);

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@DeleteMapping(value = "delete") // mapeia a url
	@ResponseBody // descricao da resposta
	public ResponseEntity<String> delete(@RequestParam Long iduser) { // recebe os dados para salvar

		usuarioRepository.deleteById(iduser);

		return new ResponseEntity<String>("Usuario deletado com sucesso", HttpStatus.OK);
	}

	@GetMapping(value = "buscaruserid") // mapeia a url
	@ResponseBody // descricao da resposta
	public ResponseEntity<Usuario> buscarUserId(@RequestParam(name = "iduser") Long iduser) { // recebe os dados para
																								// salvar

		Usuario user = usuarioRepository.findById(iduser).get();

		return new ResponseEntity<Usuario>(user, HttpStatus.OK);
	}

	@GetMapping(value = "buscarpornome") // mapeia a url
	@ResponseBody // descricao da resposta
	public ResponseEntity<List<Usuario>> buscarPorNome(@RequestParam(name = "name") String name) { // recebe os dados
																									// para
																									// salvar
		List<Usuario> usuario = usuarioRepository.buscarPorNome(name.trim().toUpperCase());

		return new ResponseEntity<List<Usuario>>(usuario, HttpStatus.OK);
	}
}
