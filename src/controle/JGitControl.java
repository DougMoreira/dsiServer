package controle;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.CanceledException;
import org.eclipse.jgit.api.errors.ConcurrentRefUpdateException;
import org.eclipse.jgit.api.errors.DetachedHeadException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidConfigurationException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.NoMessageException;
import org.eclipse.jgit.api.errors.RefNotFoundException;
import org.eclipse.jgit.api.errors.WrongRepositoryStateException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

/**
 *
 * @author Mario Pérez Esteso
 *
 */
public class JGitControl {

	private String localPath = "/home/douglas/logs"; // Diretório onde os arquivos de log serão criados
	// Cada dispositivo recebe um arquivo de log com o MAC Adress como nome
	// exemplo: 12:ak:ew:we:43:fd.log

	private String remotePath = "https://github.com/DougMoreira/dsiLogs.git"; // Endereço do repositório git
	private Repository localRepo; // Armazena repositório local
	private Git git; // Objeto Git da API JGit
	private CredentialsProvider cp; // Aqui será adicionado o nome de usuário e senha
	private String name = "DougMoreira"; // Nome de usuário git
	private String password = "********"; // Senha git
	private String idCommit = ""; // Aqui é armazenado o id de cada commit

	public JGitControl(String localPath, String remotePath) throws IOException {
		this.localPath = localPath;
		this.remotePath = remotePath;
		this.localRepo = new FileRepository(localPath + "/.git");
		cp = new UsernamePasswordCredentialsProvider(this.name, this.password);
		git = new Git(localRepo);
	}

	public JGitControl() throws IOException{// O construtor acima é apenas uma opção, mas este aqui que é usado
		this.localRepo = new FileRepository(localPath + "/.git");
		cp = new UsernamePasswordCredentialsProvider(this.name, this.password);
		git = new Git(localRepo);
	}

	public void cloneRepo() throws IOException, NoFilepatternException, GitAPIException {
		Git.cloneRepository()
		.setURI(remotePath)
		.setDirectory(new File(localPath))
		.call();
	}

	public void addToRepo() throws IOException, NoFilepatternException, GitAPIException {
		AddCommand add = git.add();
		add.addFilepattern(".").call(); // Aqui todos os arquivos do path local são adicionados para aguardar commit
	}

	public void commitToRepo(String message) throws IOException, NoHeadException,
	NoMessageException, ConcurrentRefUpdateException,
	JGitInternalException, WrongRepositoryStateException, GitAPIException {
		RevCommit command = git.commit().setMessage(message).call();
		this.setIdCommit(command.getName());
	}

	public void pushToRepo() throws IOException, JGitInternalException,
	InvalidRemoteException, GitAPIException {
		PushCommand pc = git.push();
		pc.setCredentialsProvider(cp)
		.setForce(true)
		.setPushAll();
		try {
			Iterator<PushResult> it = pc.call().iterator();
			if (it.hasNext()) {
			}
		} catch (InvalidRemoteException e) {
			e.printStackTrace();
		}
	}

	public void pullFromRepo() throws IOException, WrongRepositoryStateException,
	InvalidConfigurationException, DetachedHeadException,
	InvalidRemoteException, CanceledException, RefNotFoundException,
	NoHeadException, GitAPIException {
		git.pull().call();
	}

	public String getIdCommit() {
		return idCommit;
	}

	public void setIdCommit(String idCommit) {
		this.idCommit = idCommit;
	}

}