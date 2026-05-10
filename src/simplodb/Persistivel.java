package simplodb;

import java.io.Serializable;

/**
 * Interface que toda entidade persistida pelo SimploDB deve implementar.
 *
 * Estende Serializable para que o ArquivoMotor possa usar ObjectOutputStream
 * para gravar os objetos em arquivos .dat.
 */
public interface Persistivel extends Serializable {

    Long getId();

    void setId(Long id);
}
