declare module '@apiverve/sslchecker' {
  export interface sslcheckerOptions {
    api_key: string;
    secure?: boolean;
  }

  export interface sslcheckerResponse {
    status: string;
    error: string | null;
    data: any;
    code?: number;
  }

  export default class sslcheckerWrapper {
    constructor(options: sslcheckerOptions);

    execute(callback: (error: any, data: sslcheckerResponse | null) => void): Promise<sslcheckerResponse>;
    execute(query: Record<string, any>, callback: (error: any, data: sslcheckerResponse | null) => void): Promise<sslcheckerResponse>;
    execute(query?: Record<string, any>): Promise<sslcheckerResponse>;
  }
}
