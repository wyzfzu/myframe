package com.myframe.core.util;

import java.io.*;
import java.nio.charset.Charset;

/**
 * 流工具类。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */
public final class StreamUtils {

    public static final int BUFFER_SIZE = 4 * 1024;

    public static BufferedInputStream toBufferedStream(InputStream is) {
        return new BufferedInputStream(is);
    }

    public static BufferedOutputStream toBufferedStream(OutputStream os) {
        return new BufferedOutputStream(os);
    }

    public static BufferedReader toBufferedReader(InputStream is) {
        return toBufferedReader(is, Encoding.UTF8);
    }

    public static BufferedReader toBufferedReader(InputStream is, Charset charset) {
        return new BufferedReader(new InputStreamReader(is, charset));
    }

    public static BufferedWriter toBufferedWriter(OutputStream os) {
        return toBufferedWriter(os, Encoding.UTF8);
    }

    public static BufferedWriter toBufferedWriter(OutputStream os, Charset charset) {
        return new BufferedWriter(new OutputStreamWriter(os, charset));
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                // ignore
            }
        }
    }

    public static void close(InputStream in) {
        close((Closeable)in);
    }

    public static void closeQuietly(InputStream in) {
        closeQuietly((Closeable)in);
    }

    public static void close(OutputStream out) {
        close((Closeable)out);
    }

    public static void closeQuietly(OutputStream out) {
        closeQuietly((Closeable)out);
    }

    public static void close(Reader in) {
        close((Closeable)in);
    }

    public static void closeQuietly(Reader in) {
        closeQuietly((Closeable)in);
    }

    public static void close(Writer out) {
        close((Closeable)out);
    }

    public static void closeQuietly(Writer out) {
        closeQuietly((Closeable)out);
    }

    public static String toString(InputStream input) {
        return toString(input, Encoding.S_UTF8);
    }

    public static String toString(InputStream input, String encoding) {
        StringWriter sw = new StringWriter();
        copy(input, sw, encoding);
        return sw.toString();
    }

    public static String toString(Reader reader) {
        StringWriter sw = new StringWriter();
        copy(reader, sw);
        return sw.toString();
    }

    public static byte[] toByteArray(InputStream input) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(input, output);
        return output.toByteArray();
    }

    public static byte[] toByteArray(Reader reader) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        copy(reader, output);
        return output.toByteArray();
    }

    public static int copy(InputStream input, OutputStream output) {
        byte[] buffer = new byte[BUFFER_SIZE];
        int count = 0;
        int read;
        try {
            while (true) {
                read = input.read(buffer, 0, BUFFER_SIZE);
                if (read == -1) {
                    break;
                }
                output.write(buffer, 0, read);
                count += read;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public static int copy(InputStream input, OutputStream output, int byteCount) {
        int bufferSize = (byteCount > BUFFER_SIZE) ? BUFFER_SIZE : byteCount;

        byte buffer[] = new byte[bufferSize];
        int count = 0;
        int read;
        try {
            while (byteCount > 0) {
                if (byteCount < bufferSize) {
                    read = input.read(buffer, 0, byteCount);
                } else {
                    read = input.read(buffer, 0, bufferSize);
                }
                if (read == -1) {
                    break;
                }
                byteCount -= read;
                count += read;
                output.write(buffer, 0, read);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public static void copy(InputStream input, Writer output) {
        copy(input, output, Encoding.S_UTF8);
    }

    public static void copy(InputStream input, Writer output, int byteCount) {
        copy(input, output, Encoding.S_UTF8, byteCount);
    }

    public static void copy(InputStream input, Writer output, String encoding) {
        try {
            copy(new InputStreamReader(input, encoding), output);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(InputStream input, Writer output, String encoding, int byteCount) {
        try {
            copy(new InputStreamReader(input, encoding), output, byteCount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static int copy(Reader input, Writer output) {
        char[] buffer = new char[BUFFER_SIZE];
        int count = 0;
        int read;
        try {
            while ((read = input.read(buffer, 0, BUFFER_SIZE)) >= 0) {
                output.write(buffer, 0, read);
                count += read;
            }
            output.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public static int copy(Reader input, Writer output, int charCount) {
        int bufferSize = (charCount > BUFFER_SIZE) ? BUFFER_SIZE : charCount;

        char buffer[] = new char[bufferSize];
        int count = 0;
        int read;
        try {
            while (charCount > 0) {
                if (charCount < bufferSize) {
                    read = input.read(buffer, 0, charCount);
                } else {
                    read = input.read(buffer, 0, bufferSize);
                }
                if (read == -1) {
                    break;
                }
                charCount -= read;
                count += read;
                output.write(buffer, 0, read);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    public static void copy(Reader input, OutputStream output) {
        copy(input, output, Encoding.S_UTF8);
    }

    public static void copy(Reader input, OutputStream output, int charCount) {
        copy(input, output, Encoding.S_UTF8, charCount);
    }

    public static void copy(Reader input, OutputStream output, String encoding) {
        try {
            Writer out = new OutputStreamWriter(output, encoding);
            copy(input, out);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void copy(Reader input, OutputStream output, String encoding, int charCount) {
        try {
            Writer out = new OutputStreamWriter(output, encoding);
            copy(input, out, charCount);
            out.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
