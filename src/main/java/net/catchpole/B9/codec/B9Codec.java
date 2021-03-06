package net.catchpole.B9.codec;

import net.catchpole.B9.codec.type.Types;
import net.catchpole.B9.codec.stream.BitInputStream;
import net.catchpole.B9.codec.stream.BitOutputStream;
import net.catchpole.B9.codec.transcoder.BaseTypeTranscoder;
import net.catchpole.B9.codec.transcoder.BeanTranscoder;

import java.io.*;

/**
 Compact Binary Symmetric Serialization (for UDP broadcast of telemetry and command packets).
 The codec requires that classes are identical on both peers so that field names need not be transmitted.
 currently supported types: Strings, all primitive types including Object boxed types
 nested Object types are supported but all classes must be registered
 */
public class B9Codec implements Codec {
    private final Types types = new Types();
    private final BaseTypeTranscoder baseTypeTranscoder = new BaseTypeTranscoder();
    private final BeanTranscoder beanTranscoder = new BeanTranscoder(baseTypeTranscoder, types);
    {
        baseTypeTranscoder.setBeanTranscoder(beanTranscoder);
    }

    public void addType(Character character, Class clazz) {
        try {
            this.types.addType(character, clazz);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public byte[] encode(Object object) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(128);
        encode(object, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public Object decode(byte[] bytes) throws IOException {
        return decode(new ByteArrayInputStream(bytes));
    }

    public void encode(Object object, OutputStream outputStream) throws IOException {
        BitOutputStream bitOutputStream = new BitOutputStream(outputStream);
        beanTranscoder.write(bitOutputStream, object);
        bitOutputStream.flush();
    }

    public Object decode(InputStream inputStream) throws IOException {
        return beanTranscoder.read(new BitInputStream(inputStream));
    }
}
