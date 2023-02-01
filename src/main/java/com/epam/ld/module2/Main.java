package com.epam.ld.module2;

import static com.epam.ld.module2.testing.Mode.CONSOLE;
import static com.epam.ld.module2.testing.Mode.FILE;

import com.epam.ld.module2.testing.Client;
import com.epam.ld.module2.testing.MailServer;
import com.epam.ld.module2.testing.Messenger;
import com.epam.ld.module2.testing.Mode;
import com.epam.ld.module2.testing.template.TemplateEngine;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main
{

    private static final String FILE_INPUT_PREFIX_FLAG = "--inputFile=";
    private static final String FILE_OUTPUT_PREFIX_FLAG = "--outputFile=";

    /**
     * Entry point of the program
     *
     * @param args runtime arguments
     */
    public static void main(String[] args)
    {
        startSystem(args);
    }

    /**
     * Bootstraps the system.
     *
     * @param args runtime arguments
     */
    public static void startSystem(String[] args) {
        Mode mode = evaluateMode(args);
        Scanner scanner = evaluateInputSource(mode, args);
        PrintStream ps = evaluateOutputSource(mode, args);
        Messenger messenger = new Messenger(new MailServer(scanner, ps), new TemplateEngine());
        messenger.sendMessage(new Client());
    }

    /**
     * Evaluates working mode of the system.
     *
     * @param args runtime arguments
     * @return Mode
     */
    private static Mode evaluateMode(String[] args) {
        String arguments = String.join(" ", args);
        if (arguments.contains(FILE_INPUT_PREFIX_FLAG) && arguments.contains(FILE_OUTPUT_PREFIX_FLAG)) {
            return FILE;
        } else if (arguments.contains(FILE_INPUT_PREFIX_FLAG) || arguments.contains(FILE_OUTPUT_PREFIX_FLAG)) {
            throw new IllegalArgumentException("Either input or output file parameter is not provided");
        }

        return CONSOLE;
    }

    /**
     * Instantiates a new Scanner depends on args input.
     *
     * @param args runtime arguments
     * @return Scanner
     */
    private static Scanner evaluateInputSource(Mode mode, String[] args)
    {
        if (mode == FILE)
        {
            for (String arg : args)
            {
                if (arg.startsWith(FILE_INPUT_PREFIX_FLAG))
                {
                    try
                    {
                        return new Scanner(new File(arg.split("=")[1]), StandardCharsets.ISO_8859_1);
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return new Scanner(System.in, StandardCharsets.ISO_8859_1);
    }

    /**
     * Instantiates a PrintStream depends on args input.
     *
     * @param args runtime arguments
     * @return PrintStream
     */
    private static PrintStream evaluateOutputSource(Mode mode, String[] args)
    {
        if (mode == FILE)
        {
            for (String arg : args)
            {
                if (arg.startsWith(FILE_OUTPUT_PREFIX_FLAG))
                {
                    try
                    {
                        return new PrintStream(arg.split("=")[1], StandardCharsets.ISO_8859_1);
                    }
                    catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return System.out;
    }
}
